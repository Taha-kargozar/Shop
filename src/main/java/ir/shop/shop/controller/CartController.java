package ir.shop.shop.controller;

import ir.shop.shop.dto.requests.AddToCartRequest;
import ir.shop.shop.dto.requests.UpdateCartItemRequest;
import ir.shop.shop.dto.response.CartResponse;
import ir.shop.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {

        return ResponseEntity.ok(
                cartService.getCart()
        );

    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addProduct(
            @Valid @RequestBody AddToCartRequest request) {

        return ResponseEntity.ok(
                cartService.addProduct(request)
        );

    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateQuantity(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        return ResponseEntity.ok(
                cartService.updateQuantity(cartItemId, request)
        );

    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<String> removeProduct(
            @PathVariable Long cartItemId) {

        cartService.removeProduct(cartItemId);

        return ResponseEntity.ok(
                "Product removed from cart successfully"
        );

    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {

        cartService.clearCart();

        return ResponseEntity.ok(
                "Cart cleared successfully"
        );

    }

}
