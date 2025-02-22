package com.sandbox.springboot.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.sandbox.springboot.model.Product;

@Mapper
public interface ProductMapper {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    Long addProduct(Product product);
}