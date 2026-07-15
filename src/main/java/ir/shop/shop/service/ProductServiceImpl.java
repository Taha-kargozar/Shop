package ir.shop.shop.service;

import ir.shop.shop.dto.requests.ProductRequest;
import ir.shop.shop.dto.response.ProductResponse;
import ir.shop.shop.exception.CategoryNotFoundException;
import ir.shop.shop.exception.ProductNotFoundException;
import ir.shop.shop.model.Category;
import ir.shop.shop.model.Product;
import ir.shop.shop.repository.CategoryRepo;
import ir.shop.shop.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product product = Product.builder()
                .name(request.getName())
                .category(category)
                .price(request.getPrice())
                .Description(request.getDescription())
                .quantity(request.getQuantity())
                .build();

        Product saved = productRepo.save(product);

        return mapToResponse(saved);

    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepo.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);

        Product updatedProduct = productRepo.save(product);

        return mapToResponse(updatedProduct);

    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return mapToResponse(product);

    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepo.delete(product);

    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public List<ProductResponse> searchNameProduct(String name) {

        return productRepo.findByName(name)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    private ProductResponse mapToResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryName(product.getCategory().getName())
                .build();

    }
}
