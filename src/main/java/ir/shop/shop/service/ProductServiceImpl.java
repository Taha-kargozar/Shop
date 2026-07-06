package ir.shop.shop.service;

import ir.shop.shop.dto.requests.ProductRequest;
import ir.shop.shop.dto.response.ProductResponse;
import ir.shop.shop.repository.CategoryRepo;
import ir.shop.shop.repository.ProductRepo;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductRequest getProductById(Long id) {
        return null;
    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return List.of();
    }

    @Override
    public List<ProductResponse> searchNameProduct(String name) {
        return List.of();
    }

}
