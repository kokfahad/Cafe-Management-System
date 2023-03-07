package com.fahad.cafeteria.controller;

import com.fahad.cafeteria.constent.CafeConstants;
import com.fahad.cafeteria.jwt.JwtFilter;
import com.fahad.cafeteria.service.CategoryService;
import com.fahad.cafeteria.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtFilter jwtFilter;

    @PostMapping("/add")
    ResponseEntity<?> addNewCategory(@RequestBody Map<String,String> requestMap){
        try {
            return categoryService.addNewCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    ResponseEntity<?> getAllCategory(@RequestParam(required = false) String filterValue){
        try {
           return categoryService.getAllCategory(filterValue);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    ResponseEntity<?> updateCategory(@RequestBody Map<String,String> requestMap){
        try {
            if (jwtFilter.isAdmin()){
                return categoryService.updateCategory(requestMap);
            }
            else
                return new ResponseEntity<>(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
