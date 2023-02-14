package com.fahad.cafeteria.controller;

import com.fahad.cafeteria.constent.CafeConstants;
import com.fahad.cafeteria.jwt.JwtFilter;
import com.fahad.cafeteria.service.ProductService;
import com.fahad.cafeteria.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private JwtFilter jwtFilter;

    @PostMapping("/add")
    public ResponseEntity<?> addNewProduct(@RequestBody  Map<String, String> requestMap){
        try {
            if (jwtFilter.isAdmin())
                return productService.addNewProduct(requestMap);
            else
                return new ResponseEntity<>(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping("/get")
    ResponseEntity<?>  getAllProduct(){
        try {
            return productService.getAllProduct();
        }catch (Exception ex){
           ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    ResponseEntity<?> updateProduct(@RequestBody Map<String, String> requestMap){
        try {
            if (jwtFilter.isAdmin())
                return productService.updateProduct(requestMap);
            else
                return new ResponseEntity<>(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
