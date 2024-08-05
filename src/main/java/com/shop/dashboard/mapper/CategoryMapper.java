package com.shop.dashboard.mapper;

import com.shop.dashboard.dto.request.CategoryRequestDTO;
import com.shop.dashboard.dto.response.CategoryResponse;
import com.shop.dashboard.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category DTOToEntity(CategoryRequestDTO categoryRequestDTO) {
        return Category.builder()
                .id(categoryRequestDTO.getId())
                .categoryCode(categoryRequestDTO.getCategoryCode())
                .name(categoryRequestDTO.getName())
                .isEnable(categoryRequestDTO.isEnable())
                .build();
    }

    public CategoryResponse entityToDTO(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryCode(category.getCategoryCode())
                .isEnable(category.isEnable())
                .name(category.getName())
                .build();
    }

    public List<CategoryResponse> entityToDTOList(List<Category> categoryList) {
        return categoryList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
