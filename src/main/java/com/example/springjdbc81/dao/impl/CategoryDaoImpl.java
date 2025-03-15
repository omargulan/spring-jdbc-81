package com.example.springjdbc81.dao.impl;

import com.example.springjdbc81.dao.CategoryDao;
import com.example.springjdbc81.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Component
@AllArgsConstructor
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() {
        String sql = "select * from categories";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    @Override
    public Category findById(int id) {
        String sql = "select * from categories where id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Category create(Category category) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("categories")
                .usingGeneratedKeyColumns("id");


        Map<String, Object> map = Map.of("name", category.getName());
        int id = insert.executeAndReturnKey(map).intValue();
        category.setId(id);


        return category;
    }

    @Override
    public Category update(Category category) {
        String sql = "update categories set name = ? where id = ?";
        int rowsAffected = jdbcTemplate.update(sql, category.getName(), category.getId());
        if (rowsAffected==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); //404
        }
        return category;
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from categories  where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
