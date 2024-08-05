package com.shop.dashboard.mapper;

import com.shop.dashboard.dto.request.ProductRequestDTO;
import com.shop.dashboard.dto.response.ProductResponse;
import com.shop.dashboard.entity.Product;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    public Product ProductDTOToEntity(ProductRequestDTO productRequestDTO){
        Product product = Product.builder()
                .id(productRequestDTO.getId())
                .code_string(productRequestDTO.code_string)
                .description(productRequestDTO.getDescription())
                .name(productRequestDTO.name)
                .price(productRequestDTO.price)
                .store(productRequestDTO.store)
                .level(productRequestDTO.level)
                .article_type(productRequestDTO.article_type)
                .img(productRequestDTO.img)
                .model_number(productRequestDTO.model_number)
                .brand(productRequestDTO.brand)
                .enable(productRequestDTO.enable)
                .stock(productRequestDTO.stock)
                .availability(productRequestDTO.availability)
                .season_code(productRequestDTO.season_code)
                .build();
        return product;
    }

    public ProductResponse entityToDTO(Product product){
        ProductResponse productResponseDTO = ProductResponse.builder()
                .id(product.getId())
                .code_string(product.getCode_string())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .store(product.getStore())
                .level(product.getLevel())
                .article_type(product.getArticle_type())
                .img(product.getImg())
                .model_number(product.getModel_number())
                .brand(product.getBrand())
                .enable(product.isEnable())
                .stock(product.getStock())
                .availability(product.getAvailability())
                .season_code(product.getSeason_code())
                .build();

        if(ObjectUtils.isNotEmpty(product.getCategory())){
            productResponseDTO.setCategory(categoryMapper.entityToDTO(product.getCategory()));
        }
        return productResponseDTO;
    }

    public List<ProductResponse> entityToDTOList(List<Product> productList){
        return productList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

}
