package com.sandbox.springboot.service;

import org.springframework.stereotype.Service;

import com.sandbox.springboot.exception.ProductNotFoundException;
import com.sandbox.springboot.mapper.ProductMapper;
import com.sandbox.springboot.model.Product;

import java.util.List;

@Service
public class ProductService {
    
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public Product findById(Long id) {
        return productMapper.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public List<Product> findAll() {
        return productMapper.findAll();
    }

    public Long addProduct(Product product) {
        return productMapper.addProduct(product);
    }
}