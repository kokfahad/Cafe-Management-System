package com.fahad.cafeteria.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoryService {
    ResponseEntity<?> addNewCategory(Map<String, String> requestMap);
    ResponseEntity<?> getAllCategory(String filterValue);
    ResponseEntity<?> updateCategory(Map<String, String> requestMap);
}
