package ir.shop.shop.service;

import ir.shop.shop.dto.requests.CategoryRequest;
import ir.shop.shop.dto.response.CategoryResponse;
import ir.shop.shop.exception.CategoryNotFoundException;
import ir.shop.shop.model.Category;
import ir.shop.shop.repository.CategoryRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {

        Category category = Category.builder()
                .name(request.getName())
                .build();

        Category saved = categoryRepo.save(category);

        return new CategoryResponse(saved.getId() , saved.getName());

    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        Category category = categoryRepo.findById(id).
                orElseThrow(CategoryNotFoundException::new);

        category.setName(request.getName());

        Category updatedCategory = categoryRepo.save(category);


        return new CategoryResponse(
                updatedCategory.getId(),
                updatedCategory.getName()
        );

    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepo.delete(category);

    }

    @Override
    public CategoryResponse getCategoryById(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(CategoryNotFoundException::new);


        return new CategoryResponse(
                category.getId(),
                category.getName()
        );

    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepo.findAll()
                .stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName()
                ))
                .toList();

    }

}
