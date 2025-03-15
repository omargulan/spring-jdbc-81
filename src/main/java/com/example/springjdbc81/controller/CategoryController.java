package com.example.springjdbc81.controller;

import com.example.springjdbc81.dao.CategoryDao;
import com.example.springjdbc81.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryDao categoryDao;

    @GetMapping
    public List<Category> findAll(){
        return categoryDao.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable int id){
        return categoryDao.findById(id);
    }

    @PostMapping
    public Category create(@RequestBody Category category){
        return categoryDao.create(category);
    }
    @PutMapping
    public Category update(@RequestBody Category category){
        return categoryDao.update(category);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id){
        categoryDao.deleteById(id);
    }


}
