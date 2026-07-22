package ir.shop.shop.controller;

import ir.shop.shop.dto.requests.ProductRequest;
import ir.shop.shop.dto.response.ProductResponse;
import ir.shop.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request
    ) {

        return ResponseEntity.ok(productService.createProduct(request));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(productService.updateProduct(id, request));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String name) {

        return ResponseEntity.ok(productService.searchNameProduct(name));

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

}
