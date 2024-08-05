package com.shop.dashboard.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryResponseDTO {
    private List<CategoryResponse> categoryResponseList;
    private CategoryResponse categoryResponse;
    private String message;

    public CategoryResponseDTO(){}
    public CategoryResponseDTO(String message){
        this.message = message;
    }
    public CategoryResponseDTO(String message, CategoryResponse categoryResponse) {
        this.categoryResponse = categoryResponse;
        this.message = message;
    }

    public CategoryResponseDTO(String message, List<CategoryResponse> categoryResponseList, CategoryResponse categoryResponse) {
        this.categoryResponseList = categoryResponseList;
        this.categoryResponse = categoryResponse;
        this.message = message;
    }
}
