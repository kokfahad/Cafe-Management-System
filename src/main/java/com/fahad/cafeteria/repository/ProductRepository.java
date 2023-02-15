package com.fahad.cafeteria.repository;

import com.fahad.cafeteria.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(Integer id);

    @Query(value = "select * from Product p where p.category_fk = :id and p.status = 'true'", nativeQuery = true)
    List<Product> findProductsByCategory(@Param("id") Integer id);
}
