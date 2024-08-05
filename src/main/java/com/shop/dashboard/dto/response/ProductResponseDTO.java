package com.shop.dashboard.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductResponseDTO {
    private String message;
    private ProductResponse productResponse;
    private List<ProductResponse> productResponseList;

    public ProductResponseDTO(String message){
        this.message = message;
    }
    public ProductResponseDTO(String message, ProductResponse productResponse) {
        this.message = message;
        this.productResponse = productResponse;
    }

    public ProductResponseDTO(String message, ProductResponse productResponse, List<ProductResponse> productResponseList) {
        this.message = message;
        this.productResponse = productResponse;
        this.productResponseList = productResponseList;
    }
}
