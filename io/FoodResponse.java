package in.Kanika.foodorder.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FoodResponse {

    private String id;
    private String name;
    private String category;
    private String imageUrl;
    private double price;
    private String description;
}
