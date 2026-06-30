package com.Ecommerce.E_commerce.repository;

import com.Ecommerce.E_commerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);

}
