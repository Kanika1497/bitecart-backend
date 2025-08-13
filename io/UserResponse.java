package in.Kanika.foodorder.io;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;

}
