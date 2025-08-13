package in.Kanika.foodorder.controller;

import in.Kanika.foodorder.io.UserRequest;
import in.Kanika.foodorder.io.UserResponse;
import in.Kanika.foodorder.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

}
