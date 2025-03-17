package com.shop.dashboard.controller;

import com.shop.dashboard.dto.request.ProductRequestDTO;
import com.shop.dashboard.dto.response.ProductResponse;
import com.shop.commons.data.ResponseDTO;
import com.shop.dashboard.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/dashboard/products")
@Slf4j
public class ProductController{

    @Qualifier("productServiceImpl")
    private final CrudService<ResponseDTO<ProductResponse>, ProductRequestDTO> productService;
    public ProductController( @Qualifier("productServiceImpl") CrudService<ResponseDTO<ProductResponse>, ProductRequestDTO> productService){
        this.productService = productService;
    }
    @PostMapping("/")
    public ResponseEntity<ResponseDTO<ProductResponse>> saveProduct(@RequestBody ProductRequestDTO product){
        ResponseDTO<ProductResponse> response = productService.save(product);
        if(ObjectUtils.isNotEmpty(response.getErrors())){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<ResponseDTO<ProductResponse>> listProducts(@RequestParam(required = false) String query,
                                                           @RequestParam(required = false) String sort,
                                                           @RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer pageSize){
        ResponseDTO<ProductResponse> response = productService.getAll(query, sort, page, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductResponse>> getProductById(@PathVariable String id) {
        ResponseDTO<ProductResponse> response = productService.getById(Long.valueOf(id));
        if(ObjectUtils.isNotEmpty(response.getErrors())){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductResponse>> deleteProductById(@PathVariable Integer id) {
        ResponseDTO<ProductResponse> response = productService.delete(Long.valueOf(id));
        if(ObjectUtils.isNotEmpty(response)){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ResponseDTO<ProductResponse>> updateProductById(@RequestBody ProductRequestDTO product) {
        ResponseDTO<ProductResponse> response = productService.update(product);
        if(ObjectUtils.isNotEmpty(response.getErrors())){
            return (response.getCodeStatus() == HttpStatus.BAD_REQUEST.value())
                    ? new ResponseEntity<>(response, HttpStatus.BAD_REQUEST)
                    : new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
