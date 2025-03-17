package com.shop.dashboard.repository;

import com.shop.dashboard.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>, CrudRepository<Category, Long> {

    Page<Category> findAllByEnabled( byte enabled, Pageable pageable);
    Page<Category> findAllByDeleted(byte deleted, Pageable pageable);

    List<Category> findByName(String name);

}
