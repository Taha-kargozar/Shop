package ir.shop.shop.service;

import ir.shop.shop.dto.requests.CategoryRequest;
import ir.shop.shop.dto.response.CategoryResponse;
import ir.shop.shop.repository.CategoryRepo;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return List.of();
    }

}
