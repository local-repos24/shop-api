package com.shop.commons.utils;

import com.shop.commons.data.Pagination;
import com.shop.dashboard.entity.Category;
import org.springframework.data.domain.Page;

public class PaginationUtils {
    public static int decreaseIndexAndStartFromOne(int page){
        return page > 0 ? page -1 : page;
    }

    public static <T> Pagination getPagination(Page<T> pagedResult){
        Pagination pagination = new Pagination();
        pagination.setPageSize(pagedResult.getSize());
        pagination.setPageNumber(pagedResult.getNumber());
        pagination.setTotalPages(pagedResult.getTotalPages());
        pagination.setTotalElements(pagedResult.getTotalElements());
        pagination.setNumberOfElements(pagedResult.getNumberOfElements());

        return pagination;
    }
}
