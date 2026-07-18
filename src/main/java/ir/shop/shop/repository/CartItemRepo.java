package ir.shop.shop.repository;

import ir.shop.shop.model.Cart;
import ir.shop.shop.model.CartItem;
import ir.shop.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findAllByCart(Cart cart);

}
