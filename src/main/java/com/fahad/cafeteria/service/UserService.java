package com.fahad.cafeteria.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

    ResponseEntity<?> signUp(Map<String, String> requestMap);
    boolean validateSignUpMap(Map<String, String> requestMap);
}
