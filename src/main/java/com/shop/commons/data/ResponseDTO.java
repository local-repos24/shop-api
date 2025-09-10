package com.shop.commons.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDTO<T> {
    private List<T> responseListDTO;
    private T responseDTO;
    private String message;

    private int codeStatus;

    private Pagination pagination;

    private List<String> errors;

    public ResponseDTO(){}
    public ResponseDTO(String message){
        this.message = message;
    }
    public ResponseDTO(String message, T response) {
        this.responseDTO = response;
        this.message = message;
    }

    public ResponseDTO(String message, List<T> responseList, T response) {
        this.responseListDTO = responseList;
        this.responseDTO = response;
        this.message = message;
    }
}
