package com.shop.dashboard.service.impl;

import com.shop.commons.data.Pagination;
import com.shop.commons.utils.PaginationUtils;
import com.shop.dashboard.dto.request.CategoryRequestDTO;
import com.shop.dashboard.dto.response.CategoryResponse;
import com.shop.commons.data.ResponseDTO;
import com.shop.dashboard.entity.Category;
import com.shop.dashboard.mapper.CategoryMapper;
import com.shop.dashboard.repository.CategoryRepository;
import com.shop.dashboard.service.CrudService;
import com.shop.dashboard.utils.ValidationUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shop.commons.utils.PaginationUtils.getPagination;

@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CrudService<ResponseDTO<CategoryResponse>, CategoryRequestDTO> {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ResponseDTO<CategoryResponse> getAll(String query, String sort, Integer page, Integer pageSize) {
        ResponseDTO<CategoryResponse> responseDTO = new ResponseDTO();
        Pageable pageable = PageRequest.of(PaginationUtils.decreaseIndexAndStartFromOne(page),pageSize);
        Page<Category> pagedResult = Page.empty();

        if(StringUtils.isNotEmpty(query)) {
            switch (query.toUpperCase()) {
                case "ALL" -> pagedResult = categoryRepository.findAll(pageable);
                case "ENABLED" -> pagedResult =  categoryRepository.findAllByEnabled((byte) 1, pageable);
                case "NON-DELETED" -> pagedResult = categoryRepository.findAllByDeleted((byte) 0, pageable);
                default -> System.out.println("query does not exist");
            }
        }

        if(!pagedResult.getContent().isEmpty()){
            List<CategoryResponse> categoriesDTOResponse = categoryMapper.entityToDTOList(pagedResult.getContent());
            responseDTO.setResponseListDTO(categoriesDTOResponse);
        }
        Pagination pagination = getPagination(pagedResult);
        responseDTO.setPagination(pagination);
        responseDTO.setMessage("success");
        return responseDTO;
    }


    @Override
    public ResponseDTO<CategoryResponse> save(CategoryRequestDTO category) {
        ResponseDTO<CategoryResponse> responseDTO = new ResponseDTO<>();
        List<String> errorsList = new ArrayList<>();

        validate(category, errorsList);
        if(errorsList.size()>0){
            responseDTO.setCodeStatus(400);
            responseDTO.setErrors(errorsList);
            return responseDTO;
        }
        Category categoryMapped = categoryMapper.DTOToEntity(category);
        categoryMapped.setId(null);
        responseDTO = new ResponseDTO<>("saved successfully",
                categoryMapper.entityToDTO(categoryRepository.save(
                        categoryMapped
                )));
        return responseDTO;
    }

    @Override
    public ResponseDTO<CategoryResponse> getById(Long id) {
        ResponseDTO<CategoryResponse> responseDTO = new ResponseDTO<>();
        List<String> errors = new ArrayList<>();
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            ValidationUtils.notFound(id, errors, "Category");
            responseDTO.setErrors(errors);
            responseDTO.setCodeStatus(404);
            return responseDTO;
        }

        responseDTO.setResponseDTO(categoryMapper.entityToDTO(categoryRepository.findById(id).get()));
        return responseDTO;
    }

    @Override
    public ResponseDTO<CategoryResponse> delete(Long id) {
        ResponseDTO<CategoryResponse> responseDTO = new ResponseDTO<>();
        Optional<Category> category = categoryRepository.findById(id);
        List<String> errors = new ArrayList<>();

        if(category.isEmpty()){
            ValidationUtils.notFound(id, errors, "Category");
            responseDTO.setErrors(errors);
            responseDTO.setCodeStatus(404);
            return responseDTO;
        }

        Category categoryDeleted = category.get();
        categoryDeleted.setDeleted((byte)1);
        categoryRepository.save(categoryDeleted);
        responseDTO.setCodeStatus(200);
        responseDTO.setMessage("success");
         return responseDTO;
    }

    @Override
    public ResponseDTO<CategoryResponse> update(CategoryRequestDTO categoryRequest) {
        ResponseDTO<CategoryResponse> responseDTO = new ResponseDTO<>();
        Optional<Category> category = Optional.ofNullable(null);

        category = categoryRepository.findById(categoryRequest.getId());
        List<String> errorsList = new ArrayList<>();

        validate(categoryRequest, errorsList);
        if(errorsList.size()>0){
            responseDTO.setCodeStatus(400);
            responseDTO.setErrors(errorsList);
            return responseDTO;
        }

        if(category.isEmpty()){
            ValidationUtils.notFound(categoryRequest.getId(), errorsList, "Category");
            responseDTO.setErrors(errorsList);
            responseDTO.setCodeStatus(404);
            return responseDTO;
        }

        Category categoryUpdated = categoryRepository.save(categoryMapper.DTOToEntity(categoryRequest));

        if(ObjectUtils.isNotEmpty(categoryUpdated)){
            responseDTO.setResponseDTO(categoryMapper.entityToDTO(categoryUpdated));
            responseDTO.setMessage("success");
        }
        return responseDTO;
    }

    private void validate(CategoryRequestDTO categoryRequestDTO, List<String> errors){
        if(ObjectUtils.isEmpty(categoryRequestDTO.getName())){
            errors.add("Name should not be empty");
        }
        if(ObjectUtils.isNotEmpty(categoryRequestDTO.getName())){
            List<Category> caegories = categoryRepository.findByName(categoryRequestDTO.getName());
            if(ObjectUtils.isNotEmpty(caegories))
            {
                errors.add(String.format("The category: %s already exists", categoryRequestDTO.getName()));
            }
        }
        if(ObjectUtils.isEmpty(categoryRequestDTO.getCategoryCode())){
            errors.add("Category code should not be empty");
        }
    }

}
