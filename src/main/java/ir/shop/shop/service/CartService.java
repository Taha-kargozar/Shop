package ir.shop.shop.service;

import ir.shop.shop.dto.requests.AddToCartRequest;
import ir.shop.shop.dto.requests.UpdateCartItemRequest;
import ir.shop.shop.dto.response.CartResponse;

public interface CartService {

    CartResponse getCart();

    CartResponse addProduct(AddToCartRequest request);

    CartResponse updateQuantity(Long cartItemId, UpdateCartItemRequest request);

    void removeProduct(Long cartItemId);

    void clearCart();

}
