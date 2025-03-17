package com.shop.commons.data;

import lombok.Data;

@Data
public class Pagination {
    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
}
