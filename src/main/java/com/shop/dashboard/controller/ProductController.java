package com.shop.dashboard.controller;

import com.shop.dashboard.dto.request.ProductRequestDTO;
import com.shop.dashboard.dto.response.ProductResponseDTO;
import com.shop.dashboard.service.CrudService;
import lombok.extern.slf4j.Slf4j;
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
    private final CrudService<ProductResponseDTO, ProductRequestDTO> productService;
    public ProductController( @Qualifier("productServiceImpl") CrudService<ProductResponseDTO, ProductRequestDTO> productService){
        this.productService = productService;
    }
    @PostMapping("/")
    public ResponseEntity<ProductResponseDTO> saveProduct(@RequestBody ProductRequestDTO product){
        log.info("Request: {}", product);
        ProductResponseDTO response = productService.save(product);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<ProductResponseDTO> listProducts(){
        ProductResponseDTO response = productService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable String id) {
        ProductResponseDTO response = productService.getById(Long.valueOf(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> deleteProductById(@PathVariable Integer id) {
        ProductResponseDTO response = productService.delete(Long.valueOf(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ProductResponseDTO> updateProductById(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO response = productService.update(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
