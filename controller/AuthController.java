package in.Kanika.foodorder.controller;

import in.Kanika.foodorder.io.AuthenticationRequest;
import in.Kanika.foodorder.io.AuthenticationResponse;
import in.Kanika.foodorder.service.AppUserDetailsService;
import in.Kanika.foodorder.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtutil;
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        final UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtutil.generateToken(userDetails);

        return new AuthenticationResponse(request.getEmail(), jwtToken);
    }
}
