package in.Kanika.foodorder.controller;

import in.Kanika.foodorder.entity.CartEntity;
import in.Kanika.foodorder.io.CartRequest;
import in.Kanika.foodorder.io.CartResponse;
import in.Kanika.foodorder.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    @PostMapping
   public CartResponse addToCart(@RequestBody CartRequest request){
     String foodId=request.getFoodId();
     if(foodId==null || foodId.isEmpty()){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FoodId not found");
     }
     return cartService.addToCart(request);
    }
    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearCart();
    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request){
        String foodId=request.getFoodId();
        if(foodId==null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FoodId not found");
        }
       return cartService.removeFromCart(request);
    }

}
