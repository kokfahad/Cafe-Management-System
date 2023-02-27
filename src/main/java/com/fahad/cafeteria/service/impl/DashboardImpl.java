package com.fahad.cafeteria.service.impl;

import com.fahad.cafeteria.repository.BillRepository;
import com.fahad.cafeteria.repository.CategoryRepository;
import com.fahad.cafeteria.repository.ProductRepository;
import com.fahad.cafeteria.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardImpl implements DashboardService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillRepository billRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("category", categoryRepository.count());
            map.put("product", productRepository.count());
            map.put("bill", billRepository.count());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
