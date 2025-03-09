package com.example.springjdbc81.dao;

import com.example.springjdbc81.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(int id);
    Category create(Category category);
    Category update(Category category);
    void deleteById(int id);
}
