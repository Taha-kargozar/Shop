package ir.shop.shop.repository;

import ir.shop.shop.model.Cart;
import ir.shop.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long>{

    Optional<Cart> findByUser(User user);

}

