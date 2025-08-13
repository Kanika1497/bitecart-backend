package in.Kanika.foodorder.service;

import in.Kanika.foodorder.entity.FoodEntity;
import in.Kanika.foodorder.io.FoodRequest;
import in.Kanika.foodorder.io.FoodResponse;
import in.Kanika.foodorder.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class FoodServiceImpl implements FoodService{
    @Autowired
    private  S3Client s3Client;
    @Autowired
    private  FoodRepository foodRepository;

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    //This method is responsible for uploading image to s3 bucket
    @Override
    public String uploadFile(MultipartFile file){
        String fileNameExtension=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
        String key= UUID.randomUUID().toString()+"."+fileNameExtension;
        try{
            PutObjectRequest putObjectRequest= PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();

            PutObjectResponse response=s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            if(response.sdkHttpResponse().isSuccessful()){
                return "http://"+bucketName+".s3.amazonaws.com/"+key;

            }
            else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File upload failed");
            }

        }catch(IOException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred while uploading file to s3");
        }

    }

    @Override
    public boolean deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest= DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return true;
    }

    @Override
    public FoodResponse addFoods(FoodRequest request, MultipartFile file) {
        FoodEntity newFoodEntity=convertToEntity(request);
        String imageUrl=uploadFile(file);
        newFoodEntity.setImageUrl(imageUrl);
        newFoodEntity= foodRepository.save(newFoodEntity);
        return convertToResponse(newFoodEntity);
    }

    @Override
    public List<FoodResponse> readFoods() {
        List<FoodEntity> dbEntries=foodRepository.findAll();
        List<FoodResponse> listOfResponse=new ArrayList<>();
        for(FoodEntity foodEntity:dbEntries){
            FoodResponse foodResponse = convertToResponse(foodEntity);
            listOfResponse.add(foodResponse);
        }

        //java8 feature
        //return dbEntries.stream().map(object->convertToResponse(object)).collect(Collectors.toList());
        return listOfResponse;
    }



    @Override
    public FoodResponse readFood(String id) {
        FoodEntity dbEntry=foodRepository.findById(id).orElseThrow(()->new RuntimeException("Food not found "));
        return  convertToResponse(dbEntry);
    }

    @Override
    public void deleteFood(String id){
       FoodResponse response= readFood(id);
       String imageUrl= response.getImageUrl();
      String fileName= imageUrl.substring(imageUrl.lastIndexOf('/')+1);
        boolean isFileDeleted=deleteFile(fileName);
        if(isFileDeleted) {
            foodRepository.deleteById(response.getId());
        }
    }

    private FoodEntity convertToEntity(FoodRequest request){
        return FoodEntity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .description(request.getDescription())
                .build();
    }

    private FoodResponse convertToResponse(FoodEntity foodEntity){
        return FoodResponse.builder()
                .name(foodEntity.getName())
                .price(foodEntity.getPrice())
                .category(foodEntity.getCategory())
                .imageUrl(foodEntity.getImageUrl())
                .id(foodEntity.getId())
                .description(foodEntity.getDescription())
                .build();
    }

}
