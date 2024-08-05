package com.shop.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProductResponse {
    private long id;
    private String code_string;
    private String description;
    private String name;
    private String price;
    private int store;
    private int level;
    private String article_type;
    private String img;
    private int model_number;
    private String brand;
    private boolean enable;
    private Date last_update;
    private int stock;
    private String availability;
    private String season_code;
    private CategoryResponse category;

}
