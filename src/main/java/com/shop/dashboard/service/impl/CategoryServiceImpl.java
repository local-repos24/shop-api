package com.shop.dashboard.service.impl;

import com.shop.dashboard.dto.request.CategoryRequestDTO;
import com.shop.dashboard.dto.response.CategoryResponse;
import com.shop.dashboard.dto.response.CategoryResponseDTO;
import com.shop.dashboard.dto.response.ProductResponseDTO;
import com.shop.dashboard.entity.Category;
import com.shop.dashboard.mapper.CategoryMapper;
import com.shop.dashboard.repository.CategoryRepository;
import com.shop.dashboard.service.CrudService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CrudService<CategoryResponseDTO, CategoryRequestDTO> {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponseDTO getAll() {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO("success",categoryMapper.entityToDTOList(categoryRepository.findAll()), null);
        return responseDTO;
    }

    @Override
    public CategoryResponseDTO save(CategoryRequestDTO category) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO("saved successfully",
                categoryMapper.entityToDTO(categoryRepository.save(
                        categoryMapper.DTOToEntity(category)
                )));
        return responseDTO;
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO("success",
                categoryMapper.entityToDTO(categoryRepository.findById(id).get()));
        return responseDTO;
    }

    @Override
    public CategoryResponseDTO delete(Long id) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        Optional<Category> category = categoryRepository.findById(id);

        if(!category.isPresent()){
            System.out.println("category does not exist in the DB");
        }
         categoryRepository.deleteById(id);
        responseDTO.setMessage("deleted successfully");
         return responseDTO;
    }

    @Override
    public CategoryResponseDTO update(CategoryRequestDTO categoryRequest) {
        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        Optional<Category> category = categoryRepository.findById(categoryRequest.getId());

        if(!category.isPresent()){
            System.out.println("category does not exist in the DB");
        }

        Category categoryUpdated = categoryRepository.save(categoryMapper.DTOToEntity(categoryRequest));

        if(ObjectUtils.isNotEmpty(categoryUpdated)){
            responseDTO.setCategoryResponse(categoryMapper.entityToDTO(categoryUpdated));
            responseDTO.setMessage("updated successfully");
        }
        return responseDTO;
    }
}
