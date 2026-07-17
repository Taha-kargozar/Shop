package ir.shop.shop.repository;

import ir.shop.shop.model.Category;
import ir.shop.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {

    Optional<Product> findByName(String name);

    // Optional<Product> findByCategory(String name);

    boolean existsByCategory(Category category);

}
