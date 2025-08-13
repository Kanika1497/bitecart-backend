package in.Kanika.foodorder.service;

import in.Kanika.foodorder.entity.CartEntity;
import in.Kanika.foodorder.io.CartRequest;
import in.Kanika.foodorder.io.CartResponse;

import java.util.Optional;

public interface CartService {
    CartResponse addToCart(CartRequest request);
    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);
}
