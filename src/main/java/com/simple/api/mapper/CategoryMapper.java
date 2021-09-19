package com.simple.api.mapper;

import com.simple.api.model.CategoryDTO;
import com.simple.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categorytoCategoryDTO(Category category);

}
