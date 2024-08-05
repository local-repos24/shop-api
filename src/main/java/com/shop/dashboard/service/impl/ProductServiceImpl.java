package com.shop.dashboard.service.impl;

import com.shop.dashboard.dto.request.ProductRequestDTO;
import com.shop.dashboard.dto.response.ProductResponse;
import com.shop.dashboard.dto.response.ProductResponseDTO;
import com.shop.dashboard.entity.Category;
import com.shop.dashboard.entity.Product;
import com.shop.dashboard.mapper.ProductMapper;
import com.shop.dashboard.repository.CategoryRepository;
import com.shop.dashboard.repository.ProductRepository;
import com.shop.dashboard.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("productServiceImpl")
@RequiredArgsConstructor
public class ProductServiceImpl implements CrudService<ProductResponseDTO, ProductRequestDTO> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO getAll() {
        List<ProductResponse> productResponseDTOList = productMapper.entityToDTOList(productRepository.findAll());

        ProductResponseDTO responseDTO = new ProductResponseDTO("success",null,productResponseDTOList);
        return responseDTO;
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.ProductDTOToEntity(productRequestDTO);

        Optional<Category> category = categoryRepository.findById(productRequestDTO.getCategory_id());

        if(category.isPresent()){
            product.setCategory(category.get());
        }

        return new ProductResponseDTO("success",productMapper.entityToDTO(productRepository.save(product)));
    }

    @Override
    public ProductResponseDTO getById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(!product.isPresent()){
            System.out.println("Not Found");
        }

        ProductResponseDTO responseDTO = new ProductResponseDTO("success", productMapper.entityToDTO(product.get()));
        return responseDTO;
    }

    @Override
    public ProductResponseDTO delete(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(!product.isPresent()){
            System.out.println("Product ID does not eixst in the DB");
        }

        productRepository.delete(product.get());
        ProductResponseDTO responseDTO = new ProductResponseDTO("deleted succcessfully", null);
        return responseDTO;
    }

    @Override
    public ProductResponseDTO update(ProductRequestDTO productRequest) {
        Optional<Product> product = productRepository.findById(productRequest.getId());

        if(!product.isPresent()){
            System.out.println("Not Found");
        }

        Product productEntity = productMapper.ProductDTOToEntity(productRequest);
        productEntity.setLast_update(new Date());

        Optional<Category> category = categoryRepository.findById(productRequest.getCategory_id());

        if(category.isPresent()){
            productEntity.setCategory(category.get());
        }

        ProductResponseDTO responseDTO = new ProductResponseDTO("updated successfully", productMapper.entityToDTO(productRepository.save(productEntity)));
        return responseDTO;
    }
}
