package com.fahad.cafeteria.controller;

import com.fahad.cafeteria.constent.CafeConstants;
import com.fahad.cafeteria.service.BillService;
import com.fahad.cafeteria.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/generate-report")
    ResponseEntity<?> generateReport(@RequestBody Map<String, Object> requestMap){
        try {
           return billService.generateReport(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
