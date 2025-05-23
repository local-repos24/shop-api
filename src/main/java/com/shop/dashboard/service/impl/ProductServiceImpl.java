package com.shop.dashboard.service.impl;

import com.shop.commons.data.Pagination;
import com.shop.commons.utils.PaginationUtils;
import com.shop.dashboard.dto.request.ProductRequestDTO;
import com.shop.dashboard.dto.response.ProductResponse;
import com.shop.commons.data.ResponseDTO;
import com.shop.dashboard.entity.Category;
import com.shop.dashboard.entity.Product;
import com.shop.dashboard.mapper.ProductMapper;
import com.shop.dashboard.repository.CategoryRepository;
import com.shop.dashboard.repository.ProductRepository;
import com.shop.dashboard.service.CrudService;
import com.shop.dashboard.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.shop.commons.utils.PaginationUtils.getPagination;

@Service("productServiceImpl")
@RequiredArgsConstructor
public class ProductServiceImpl implements CrudService<ResponseDTO<ProductResponse>, ProductRequestDTO> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    public ResponseDTO<ProductResponse> getAll(String query, String sort, Integer page, Integer pageSize) {
        ResponseDTO<ProductResponse> responseDTO = new ResponseDTO<>();
        Pageable pageable = PageRequest.of(PaginationUtils.decreaseIndexAndStartFromOne(page),pageSize);
        Page<Product> pagedResult = Page.empty();

        if(StringUtils.isNotEmpty(query)) {
            switch (query.toUpperCase()) {
                case "ALL" -> pagedResult = productRepository.findAll(pageable);
                case "ENABLED" -> pagedResult =  productRepository.findAllByEnable((byte) 1, pageable);
                default -> System.out.println("query does not exist");
            }
        }
        if(!pagedResult.getContent().isEmpty()){
            List<ProductResponse> productResponseDTOList= productMapper.entityToDTOList(pagedResult.getContent());
            responseDTO.setResponseListDTO(productResponseDTOList);
        }

        Pagination pagination = getPagination(pagedResult);
        responseDTO.setMessage("success");
        responseDTO.setPagination(pagination);
        responseDTO.setCodeStatus(200);
        return responseDTO;
    }


    @Override
    public ResponseDTO<ProductResponse> save(ProductRequestDTO productRequestDTO) {
        ResponseDTO<ProductResponse> responseDTO = new ResponseDTO<>();
        Product product = productMapper.ProductDTOToEntity(productRequestDTO);
        List<String> errors = new ArrayList<>();
        Optional<Category> category = Optional.ofNullable(null);

        validateFields(productRequestDTO, errors);
        if(errors.size() > 0){
            responseDTO.setErrors(errors);
            responseDTO.setCodeStatus(400);
            return responseDTO;
        }

        if(ObjectUtils.isNotEmpty(productRequestDTO.getCategory_id())){
            category = categoryRepository.findById(productRequestDTO.getCategory_id());
            if(category.isEmpty()){
                ValidationUtils.notFound(productRequestDTO.getCategory_id(), errors, "Category");
                responseDTO.setErrors(errors);
                responseDTO.setCodeStatus(404);
                return responseDTO;
            }
        }

        if(category.isPresent()){
            product.setCategory(category.get());
        }

        return new ResponseDTO<>("success", productMapper.entityToDTO(productRepository.save(product)));
    }

    @Override
    public ResponseDTO<ProductResponse> getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        ResponseDTO<ProductResponse> responseDTO = new ResponseDTO<>();
        List<String> errors = new ArrayList<>();

        if(product.isEmpty()){
            ValidationUtils.notFound(id, errors, "Product");
            responseDTO.setCodeStatus(404);
            responseDTO.setErrors(errors);
            return responseDTO;
        }

        responseDTO = new ResponseDTO<>("success", productMapper.entityToDTO(product.get()));
        responseDTO.setCodeStatus(200);
        return responseDTO;
    }

    @Override
    public ResponseDTO<ProductResponse> delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        ResponseDTO<ProductResponse> responseDTO = new ResponseDTO<>();
        List<String> errors = new ArrayList<>();

        if(product.isEmpty()){
            ValidationUtils.notFound(id, errors, "Product");
            responseDTO.setCodeStatus(404);
            return responseDTO;
        }

        productRepository.delete(product.get());
        responseDTO = new ResponseDTO<>("success", null);
        responseDTO.setCodeStatus(200);
        return responseDTO;
    }

    @Override
    public ResponseDTO<ProductResponse> update(ProductRequestDTO productRequest) {
        Optional<Product> product = productRepository.findById(productRequest.getId());
        ResponseDTO<ProductResponse> responseDTO = new ResponseDTO<>();
        List<String> errors = new ArrayList<>();
        Optional<Category> category = Optional.ofNullable(null);

        if(product.isEmpty()){
            ValidationUtils.notFound(productRequest.getId(), errors, "Product");
            responseDTO.setCodeStatus(404);
            responseDTO.setErrors(errors);
            return responseDTO;
        }

        if(ObjectUtils.isNotEmpty(productRequest.getCategory_id())){
            category = categoryRepository.findById(productRequest.getCategory_id());

            if(category.isEmpty()){
                ValidationUtils.notFound(productRequest.getCategory_id(), errors, "Category");
                responseDTO.setCodeStatus(404);
                responseDTO.setErrors(errors);
                return responseDTO;
            }
        }

        validateFields(productRequest, errors);
        if(errors.size()>0){
            responseDTO.setErrors(errors);
            responseDTO.setCodeStatus(400);
            return responseDTO;
        }

        Product productEntity = productMapper.ProductDTOToEntity(productRequest);
        productEntity.setLast_update(new Date());


        if(category.isPresent()){
            productEntity.setCategory(category.get());
        }

        responseDTO = new ResponseDTO<>("success", productMapper.entityToDTO(productRepository.save(productEntity)));
        responseDTO.setCodeStatus(200);
        return responseDTO;
    }

    private void validateFields(ProductRequestDTO productRequestDTO, List<String> errors){
        if(ObjectUtils.isEmpty(productRequestDTO.getCode_string())){
            errors.add("The code string should not be empty");
        }
        if(ObjectUtils.isNotEmpty(productRequestDTO.getCode_string())){
            List<Product> product = productRepository.findByCodeString(productRequestDTO.getCode_string());
            if(ObjectUtils.isNotEmpty(product)){
                errors.add(String.format("The code: %s already exists", productRequestDTO.getCode_string()));
            }
        }
        if(ObjectUtils.isEmpty(productRequestDTO.description)){
            errors.add("Description should not be empty");
        }
        if(ObjectUtils.isEmpty(productRequestDTO.getName())){
            errors.add("Name should not be empty");
        }
        if(ObjectUtils.isEmpty(productRequestDTO.getPrice())){
            errors.add("Price should not be empty");
        }
        if(productRequestDTO.getModel_number() == 0 || productRequestDTO.getModel_number() < 0){
            errors.add("Model number should not be 0 or lower than 0");
        }else{
            List<Product> product = productRepository.findByModelNumber(productRequestDTO.getModel_number());
            if(ObjectUtils.isNotEmpty(product)){
                errors.add(String.format("The model number: %s already exists", productRequestDTO.getModel_number()));
            }
        }
        if(ObjectUtils.isEmpty(productRequestDTO.getBrand())){
            errors.add("Brand should not be empty");
        }
        if(productRequestDTO.getStock() < 0 ){
            errors.add("The value for stock is not valid, minimun value is 0");
        }
        if(ObjectUtils.isEmpty(productRequestDTO.getAvailability())){
            errors.add("Availability should not be empty");
        }
    }
}
