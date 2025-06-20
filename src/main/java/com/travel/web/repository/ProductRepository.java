package com.travel.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.web.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional custom queries can be added here if needed
    Page<Product> findByCategory(String category, Pageable pageable);
   List<Product> findByProductNameContainingOrCategoryContaining(String query,String query2);

}
