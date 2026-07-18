package ir.shop.shop.service;

import ir.shop.shop.dto.requests.AddToCartRequest;
import ir.shop.shop.dto.requests.UpdateCartItemRequest;
import ir.shop.shop.dto.response.CartItemResponse;
import ir.shop.shop.dto.response.CartResponse;
import ir.shop.shop.exception.CartItemNotFoundException;
import ir.shop.shop.exception.CartNotFoundException;
import ir.shop.shop.exception.ProductNotFoundException;
import ir.shop.shop.exception.UserNotFoundException;
import ir.shop.shop.model.Cart;
import ir.shop.shop.model.CartItem;
import ir.shop.shop.model.Product;
import ir.shop.shop.model.User;
import ir.shop.shop.repository.CartItemRepo;
import ir.shop.shop.repository.CartRepo;
import ir.shop.shop.repository.ProductRepo;
import ir.shop.shop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Override
    public CartResponse getCart() {

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> createCart(user));

        return mapToResponse(cart);
    }

    @Override
    public CartResponse addProduct(AddToCartRequest request) {

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> createCart(user));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        CartItem cartItem = cartItemRepo
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if(cartItem != null){

            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();

        }

        cartItemRepo.save(cartItem);

        return mapToResponse(cart);
    }

    @Override
    public CartResponse updateQuantity(Long cartItemId,UpdateCartItemRequest request) {

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(CartNotFoundException::new);

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(CartItemNotFoundException::new);

        if(!cartItem.getCart().getId()
                .equals(cart.getId())){

            throw new AccessDeniedException(
                    "This item does not belong to user cart"
            );

        }

        cartItem.setQuantity(
                request.getQuantity()
        );

        cartItemRepo.save(cartItem);

        return mapToResponse(cart);

    }

    @Override
    public void removeProduct(Long cartItemId) {

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(CartNotFoundException::new);

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(CartItemNotFoundException::new);

        if(!cartItem.getCart().getId()
                .equals(cart.getId())){

            throw new AccessDeniedException(
                    "This item does not belong to user cart"
            );

        }

        cartItemRepo.delete(cartItem);

    }

    @Override
    public void clearCart() {

        User user = getCurrentUser();

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(CartNotFoundException::new);

        List<CartItem> items = cartItemRepo.findAllByCart(cart);

        cartItemRepo.deleteAll(items);

    }

    private Cart createCart(User user){

        Cart cart = Cart.builder()
                .user(user)
                .items(new ArrayList<>())
                .build();

        return cartRepo.save(cart);

    }

    private User getCurrentUser(){

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

    }

    private CartResponse mapToResponse(Cart cart){

        List<CartItemResponse> items =
                cart.getItems()
                        .stream()
                        .map(this::mapItem)
                        .toList();

        BigDecimal totalPrice =
                items.stream()
                        .map(CartItemResponse::getSubtotal)
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add
                        );

        return CartResponse.builder()
                .id(cart.getId())
                .items(items)
                .totalPrice(totalPrice)
                .build();

    }

    private CartItemResponse mapItem(CartItem item){

        Product product = item.getProduct();

        BigDecimal subtotal =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        );

        return CartItemResponse.builder()
                .id(item.getId())
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(item.getQuantity())
                .subtotal(subtotal)
                .build();

    }

}
