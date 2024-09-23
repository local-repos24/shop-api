package com.shop.dashboard.controller;


import com.shop.dashboard.dto.request.CategoryRequestDTO;

import com.shop.dashboard.dto.response.CategoryResponseDTO;
import com.shop.dashboard.service.CrudService;
import lombok.extern.slf4j.Slf4j;
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
    private final CrudService<CategoryResponseDTO, CategoryRequestDTO> categoryService;
    public CategoryController(@Qualifier("categoryServiceImpl") CrudService<CategoryResponseDTO, CategoryRequestDTO> categoryService){
        this.categoryService =  categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<CategoryResponseDTO> getCategories() {
        CategoryResponseDTO responseDTO = categoryService.getAll();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategory(@PathVariable long id) {
        CategoryResponseDTO responseDTO = categoryService.getById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO responseDTO = categoryService.save(categoryRequestDTO);
        log.info(String.valueOf(categoryRequestDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO responseDTO = categoryService.update(categoryRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategpry(@PathVariable long id){
        CategoryResponseDTO responseDTO = categoryService.delete(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
