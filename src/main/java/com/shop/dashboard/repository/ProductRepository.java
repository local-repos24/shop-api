package com.shop.dashboard.repository;

import com.shop.dashboard.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findAllByEnable(byte enable, Pageable pageable);

    List<Product> findByCodeString(String codeString);

    List<Product> findByModelNumber(int modelNumber);

}
