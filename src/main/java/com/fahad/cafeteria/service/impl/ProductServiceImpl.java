package com.fahad.cafeteria.service.impl;

import com.fahad.cafeteria.constent.CafeConstants;
import com.fahad.cafeteria.model.Category;
import com.fahad.cafeteria.model.Product;
import com.fahad.cafeteria.repository.ProductRepository;
import com.fahad.cafeteria.service.ProductService;
import com.fahad.cafeteria.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addNewProduct(Map<String, String> requestMap) {
        try {
            if (validateProductMap(requestMap, false)) {
                productRepository.save(getProductFromMap(requestMap, false));
                return CafeUtils.getResponseEntity("Product added successfully", HttpStatus.CREATED);
            }
            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd)
            product.setId(Integer.parseInt(requestMap.get("id")));
        else
            product.setStatus("true");

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(requestMap.get("price"));
        return product;
    }

    @Override
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> productList = productRepository.findAll();
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateProduct(Map<String, String> requestMap) {
        try {
            if (validateProductMap(requestMap, true)) {
                Optional<Product> productOptional = productRepository.findById(Integer.parseInt(requestMap.get("id")));
                if (productOptional.isPresent()) {
                    Product product = getProductFromMap(requestMap, true);
                    product.setStatus(productOptional.get().getStatus());
                    productRepository.save(product);
                    return CafeUtils.getResponseEntity("Product updated successfully !!", HttpStatus.OK);
                } else
                    return CafeUtils.getResponseEntity("Product Id doesn't exist !!", HttpStatus.BAD_REQUEST);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Integer id) {
        try {
            if (id != null) {
                Optional<Product> product = productRepository.findById(id);
                if (product.isPresent()) {
                    productRepository.deleteById(id);
                    return CafeUtils.getResponseEntity("Product deleted successfully !!", HttpStatus.OK);
                } else
                    return CafeUtils.getResponseEntity("Prdduct Id doesn't exist !!", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateStatus(Map<String, String> requestMap) {
        try {
            Integer id = Integer.parseInt(requestMap.get("id"));
            if (id != null) {
                Optional<Product> product = productRepository.findById(id);
                if (product.isPresent()) {
                    product.get().setStatus(requestMap.get("status"));
//                    productRepository.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    productRepository.save(product.get());
                    return CafeUtils.getResponseEntity("Product status updated successfully !!", HttpStatus.OK);
                } else
                    return CafeUtils.getResponseEntity("Product id doesn't exist !!", HttpStatus.BAD_REQUEST);
            } else
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getByCategory(Integer id) {
        try {
            if (id != null) {
                List<Product> productList = productRepository.findProductsByCategory(id);
                if (productList.size() > 0) {
                    return new ResponseEntity<>(productList, HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("No product found of this category !!", HttpStatus.BAD_REQUEST);
            } else
                return CafeUtils.getResponseEntity("Product id doesn't exist", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getProductById(Integer id) {
        try {
            if (id != null) {
                Optional<Product> product = productRepository.findById(id);
                if (product.isPresent()) {
                    return new ResponseEntity<>(product, HttpStatus.OK);
                } else
                    return CafeUtils.getResponseEntity("No product found !!", HttpStatus.BAD_REQUEST);
            } else
                return CafeUtils.getResponseEntity("Product id doesn't exist !!", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
