package ir.shop.shop.service;

import ir.shop.shop.dto.requests.ProductRequest;
import ir.shop.shop.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id , ProductRequest productRequest);

    ProductRequest getProductById(Long id);

    void deleteProduct();

    List<ProductResponse> getAllProducts();

    List<ProductResponse> searchNameProduct(String name);

}
