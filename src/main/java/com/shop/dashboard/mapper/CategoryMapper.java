package com.shop.dashboard.mapper;

import com.shop.dashboard.dto.request.CategoryRequestDTO;
import com.shop.dashboard.dto.response.CategoryResponse;
import com.shop.dashboard.entity.Category;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category DTOToEntity(CategoryRequestDTO categoryRequestDTO) {
        Category.CategoryBuilder builder = Category.builder()
                .id(categoryRequestDTO.getId())
                .categoryCode(categoryRequestDTO.getCategoryCode())
                .name(categoryRequestDTO.getName());

        if(ObjectUtils.isNotEmpty(categoryRequestDTO.isEnable())){
            builder.enabled((categoryRequestDTO.isEnable()) ? (byte)1 : (byte)0);
        }
        return builder.build();
    }

    public CategoryResponse entityToDTO(Category category) {
        CategoryResponse.CategoryResponseBuilder builder= CategoryResponse.builder()
                .id(category.getId())
                .categoryCode(category.getCategoryCode())
                .name(category.getName());

        if(ObjectUtils.isNotEmpty(category.getEnabled())){
            builder.isEnable(category.getEnabled() == 1);
        }
        if(ObjectUtils.isNotEmpty(category.getDeleted())){
            builder.isDeleted(category.getDeleted() == 1);
        }

        return builder.build();
    }

    public List<CategoryResponse> entityToDTOList(List<Category> categoryList) {
        return categoryList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
