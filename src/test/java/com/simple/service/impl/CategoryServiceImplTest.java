package com.simple.service.impl;

import com.simple.api.mapper.CategoryMapper;
import com.simple.api.model.CategoryDTO;
import com.simple.domain.Category;
import com.simple.repository.CategoryRepository;
import com.simple.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;

    @BeforeEach
    public void setUp(){
        categoryService = new CategoryDtoServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    public void getallCategories() throws Exception{
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTO = categoryService.getAllCategories();

        assertEquals(3,categoryDTO.size());
    }


    @Test
    public void getCategoryByName() throws Exception{
        Category category = new Category(1L, "fruit");

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName(category.getName());
        assertEquals(1L, categoryDTO.getId());
        assertEquals("fruit", categoryDTO.getName());

    }






}