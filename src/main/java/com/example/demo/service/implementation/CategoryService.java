package com.example.demo.service.implementation;
import java.util.List;
import java.util.Optional;
import com.example.demo.dto.CategoryDTO;


public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    Optional<CategoryDTO> getCategoryById(Long id);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}

