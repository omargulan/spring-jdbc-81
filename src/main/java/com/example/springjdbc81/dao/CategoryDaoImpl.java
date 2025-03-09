package com.example.springjdbc81.dao;

import com.example.springjdbc81.model.Category;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll() {
        String sql = "select * from categories";
//        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
//        List<Category> categories = new ArrayList<>();
//        while (sqlRowSet.next()){
//            int id = sqlRowSet.getInt("id");
//            String name = sqlRowSet.getString("name");
//
//            Category category = new Category(id, name);
//            categories.add(category);
//        }

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
//        String sql = "insert into categories (name) values (?)";
//        int rowsAffected = jdbcTemplate.update(sql, category.getName());
//        System.out.println("rowsAffected =  " + rowsAffected);
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
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
