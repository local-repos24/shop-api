package com.shop.dashboard.repository;

import com.shop.dashboard.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
