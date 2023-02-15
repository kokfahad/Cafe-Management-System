package com.fahad.cafeteria.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface ProductService {
    ResponseEntity<?> addNewProduct(Map<String, String> requestMap);

    ResponseEntity<?> getAllProduct();

    ResponseEntity<?> updateProduct(Map<String, String> requestMap);


    ResponseEntity<?> deleteProduct(Integer id);

    ResponseEntity<?> updateStatus(Map<String, String>requestMap);

    ResponseEntity<?> getByCategory(Integer id);

    ResponseEntity<?> getProductById(Integer id);
}
