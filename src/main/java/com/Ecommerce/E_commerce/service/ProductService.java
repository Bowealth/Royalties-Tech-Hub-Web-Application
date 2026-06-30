package com.Ecommerce.E_commerce.service;

import com.Ecommerce.E_commerce.entity.Product;
import com.Ecommerce.E_commerce.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private final ProductRepository repository;



    public ProductService(ProductRepository repository) {

        this.repository = repository;
    }

    // CREATE
    public Product saveProduct(Product product) {

        return repository.save(product);
    }

    // READ
    public List<Product> getAllProducts() {

        return repository.findAll();
    }

    // Search
    public List<Product> searchProducts(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    // GET BY ID
    public Product getProductById(Long id) {

        return repository.findById(id).orElse(null);
    }

    // DELETE
    public void deleteProduct(Long id) {

        repository.deleteById(id);
    }
}