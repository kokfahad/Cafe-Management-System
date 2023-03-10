package com.fahad.cafeteria.service.impl;

import com.fahad.cafeteria.constent.CafeConstants;
import com.fahad.cafeteria.jwt.JwtFilter;
import com.fahad.cafeteria.model.Category;
import com.fahad.cafeteria.repository.CategoryRepository;
import com.fahad.cafeteria.service.CategoryService;
import com.fahad.cafeteria.utils.CafeUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public ResponseEntity<?> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, false)) {
                    categoryRepository.save(getCategoryFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Category added successfully !!", HttpStatus.CREATED);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllCategory(String filterValue) {
        try {
//            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
//                //will fix this code later
//                List<Category> categoryList = categoryRepository.findAll();
//                return new ResponseEntity<List<Category>>( categoryList,HttpStatus.OK);
//            }
            List<Category> categoryList = categoryRepository.findAll();
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }


    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<?> updateCategory(Map<String, String> requestMap) {
        try {
            if (validateCategoryMap(requestMap, false)){
                Optional<Category> category = categoryRepository.findById(Integer.parseInt(requestMap.get("id")));
                if (category.isPresent()){
                    categoryRepository.save(getCategoryFromMap(requestMap, true));
                    return CafeUtils.getResponseEntity("Category updated successfully", HttpStatus.OK);
                }else {
                    return CafeUtils.getResponseEntity("Category Id does not exist !!", HttpStatus.OK);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
