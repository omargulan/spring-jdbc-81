package com.example.springjdbc81.dao.impl;

import com.example.springjdbc81.dao.ProductDao;
import com.example.springjdbc81.model.Category;
import com.example.springjdbc81.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Product> findAll() {
        String sql = """
                select p.id as product_id,
                        p.name as product_name,
                        p.price as product_price,
                        c.id as category_id,
                        c.name as category_name
                from products p 
                join categories c on p.category_id = c.id
                       """;

        return jdbcTemplate.query(sql,this::mapRow);
    }

    @Override
    public Product findById(int id) {
        String sql = """
                select p.id as product_id,
                        p.name as product_name,
                        p.price as product_price,
                        c.id as category_id,
                        c.name as category_name
                from products p 
                join categories c on p.category_id = c.id where p.id = ?
                       """;

        return jdbcTemplate.query(sql, this::mapRow, id)
                .stream()
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Product create(Product product) {
        return null;
    }

    public Product mapRow(ResultSet rs, int rowNum) throws SQLException{
        int id = rs.getInt("product_id");
        String name = rs.getString("product_name");
        double price = rs.getDouble("product_price");
        int categoryId = rs.getInt("category_id");
        String categoryName = rs.getString("category_name");
        Category category =new Category(categoryId, categoryName);
        //category.setId(categoryId);
        return new Product(id, name, price, category);
    }
}
