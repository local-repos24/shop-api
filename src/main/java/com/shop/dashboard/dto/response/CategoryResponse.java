package com.shop.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CategoryResponse {
    private long id;
    private String name;
    private boolean isEnable;
    private String categoryCode;

}
