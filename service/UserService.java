package in.Kanika.foodorder.service;

import in.Kanika.foodorder.io.UserRequest;
import in.Kanika.foodorder.io.UserResponse;

public interface UserService {

   UserResponse registerUser(UserRequest request);

   String findByUserId();
}
