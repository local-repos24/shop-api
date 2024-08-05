package com.shop.commons.data;

import com.shop.dashboard.dto.response.CategoryResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class StoreContextData {
    List<CategoryResponse> categories;
    CategoryResponse category;
}
