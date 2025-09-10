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
        Product.ProductBuilder product = Product.builder()
                .id(productRequestDTO.getId())
                .codeString(productRequestDTO.code_string)
                .description(productRequestDTO.getDescription())
                .name(productRequestDTO.name)
                .price(productRequestDTO.price)
                .store(productRequestDTO.store)
                .level(productRequestDTO.level)
                .article_type(productRequestDTO.article_type)
                .img(productRequestDTO.img)
                .modelNumber(productRequestDTO.model_number)
                .brand(productRequestDTO.brand)
                .stock(productRequestDTO.stock)
                .availability(productRequestDTO.availability)
                .season_code(productRequestDTO.season_code);

        if(ObjectUtils.isNotEmpty(productRequestDTO.isEnable())){
            product.enable((productRequestDTO.isEnable()) ? (byte) 1 :(byte) 0);
        }
        return product.build();
    }

    public ProductResponse entityToDTO(Product product){
        ProductResponse.ProductResponseBuilder productResponseDTO = ProductResponse.builder()
                .id(product.getId())
                .code_string(product.getCodeString())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .store(product.getStore())
                .level(product.getLevel())
                .article_type(product.getArticle_type())
                .img(product.getImg())
                .model_number(product.getModelNumber())
                .brand(product.getBrand())
                .stock(product.getStock())
                .availability(product.getAvailability())
                .season_code(product.getSeason_code())
                .last_update(product.getLast_update());

        if(ObjectUtils.isNotEmpty(product.getEnable())){
            productResponseDTO.enable(product.getEnable() == 1);
        }
        if(ObjectUtils.isNotEmpty(product.getCategory())){
            productResponseDTO.category(categoryMapper.entityToDTO(product.getCategory()));
        }
        return productResponseDTO.build();
    }

    public List<ProductResponse> entityToDTOList(List<Product> productList){
        return productList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

}
