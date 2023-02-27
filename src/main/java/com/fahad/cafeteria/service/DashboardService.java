package com.fahad.cafeteria.service;

import org.springframework.http.ResponseEntity;

public interface DashboardService {
    ResponseEntity<?> getCount();
}
