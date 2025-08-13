package in.Kanika.foodorder.repository;

import in.Kanika.foodorder.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity,String> {

}
