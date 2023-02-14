package com.fahad.cafeteria.repository;

import com.fahad.cafeteria.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
