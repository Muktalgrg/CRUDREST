package com.simple.service.impl;

import com.simple.api.mapper.CategoryMapper;
import com.simple.api.model.CategoryDTO;
import com.simple.controller.CategoryController;
import com.simple.repository.CategoryRepository;
import com.simple.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryDtoServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryDtoServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryDTO categoryDTO = categoryMapper.categorytoCategoryDTO(category);
                    return categoryDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryMapper.categorytoCategoryDTO(categoryRepository.findByName(name));
    }

}
