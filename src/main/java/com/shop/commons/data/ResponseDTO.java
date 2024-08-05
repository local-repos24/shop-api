package com.shop.commons.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    T response;
    String message;
}
