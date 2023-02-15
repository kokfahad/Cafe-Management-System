package com.fahad.cafeteria.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BillService {
    ResponseEntity<?> generateReport(Map<String, Object> requestMap);
}
