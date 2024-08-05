package com.shop.dashboard.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDTO {
    public long id;
    public String code_string;
    public String description;
    public String name;
    public String price;
    public int store;
    public int level;
    public String article_type;
    public String img;
    public int model_number;
    public String brand;
    public boolean enable;
    public int stock;
    public String availability;
    public String season_code;
    public Long category_id;
}

