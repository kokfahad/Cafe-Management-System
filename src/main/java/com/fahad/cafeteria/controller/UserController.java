package com.fahad.cafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserSer

    @PostMapping("/singUp")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String>requestMap){
       try {
           return
       }catch (){

       }
    }
}
