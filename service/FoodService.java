package in.Kanika.foodorder.service;

import in.Kanika.foodorder.io.FoodRequest;
import in.Kanika.foodorder.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {
    String uploadFile(MultipartFile file);

    boolean deleteFile(String fileName);
    FoodResponse addFoods(FoodRequest request ,MultipartFile file);

    List<FoodResponse> readFoods();

    FoodResponse readFood(String id);

   void deleteFood(String id);
}
