package com.fahad.cafeteria.service;

import com.fahad.cafeteria.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<?> signUp(Map<String, String> requestMap);
    ResponseEntity<?> login(Map<String, String> requestMap);
    boolean validateSignUpMap(Map<String, String> requestMap);
    ResponseEntity<?> getAllUser();
    ResponseEntity<?> update(Map<String, String> requestMap);
}
