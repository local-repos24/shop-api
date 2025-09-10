package com.shop.dashboard.controller;


import com.shop.dashboard.dto.request.CategoryRequestDTO;

import com.shop.dashboard.dto.response.CategoryResponse;
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
@RequestMapping("/api/dashboard/categories")
@Slf4j
public class CategoryController {

    @Qualifier("categoryServiceImpl")
    private final CrudService<ResponseDTO<CategoryResponse>, CategoryRequestDTO> categoryService;
    public CategoryController(@Qualifier("categoryServiceImpl") CrudService<ResponseDTO<CategoryResponse>, CategoryRequestDTO> categoryService){
        this.categoryService =  categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDTO<CategoryResponse>> getCategories(@RequestParam(required = false) String query,
                                                             @RequestParam(required = false) String sort,
                                                             @RequestParam(defaultValue = "1") Integer page,
                                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        ResponseDTO<CategoryResponse> responseDTO = categoryService.getAll(query, sort, page,  pageSize);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryResponse>> getCategory(@PathVariable long id) {
        ResponseDTO<CategoryResponse> responseDTO = categoryService.getById(id);

        if(ObjectUtils.isNotEmpty(responseDTO.getErrors())){
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDTO<CategoryResponse>> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        ResponseDTO<CategoryResponse> responseDTO = categoryService.save(categoryRequestDTO);
        if(ObjectUtils.isNotEmpty(responseDTO.getErrors())){
            if(responseDTO.getCodeStatus() == HttpStatus.BAD_REQUEST.value()){
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<ResponseDTO<CategoryResponse>> updateCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        ResponseDTO<CategoryResponse> responseDTO = categoryService.update(categoryRequestDTO);
        if(ObjectUtils.isNotEmpty(responseDTO.getErrors())){
            return responseDTO.getCodeStatus() == HttpStatus.BAD_REQUEST.value() ? new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST)
            : new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryResponse>> deleteCategory(@PathVariable long id){
        ResponseDTO<CategoryResponse> responseDTO = categoryService.delete(id);
        if(ObjectUtils.isNotEmpty(responseDTO.getErrors())){
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
