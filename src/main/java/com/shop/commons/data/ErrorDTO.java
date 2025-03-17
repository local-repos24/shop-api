package com.shop.commons.data;

import lombok.Data;

import java.util.List;

@Data
public class ErrorDTO {
    private List<String> errors;
}
