package com.shop.dashboard.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.commons.data.RequestType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequestDTO {
    private long id;
    private String name;
    @JsonProperty("is_enable")
    private boolean isEnable;

    @JsonProperty("category_code")
    private String categoryCode;
    private RequestType requestType;
}
