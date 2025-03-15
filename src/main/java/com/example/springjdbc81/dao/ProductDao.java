package com.example.springjdbc81.dao;

import com.example.springjdbc81.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
    Product findById(int id);
    Product create(Product product);
}
