package com.fahad.cafeteria.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    private Integer id;

    private String name;

    private String description;

    private Integer price;

    private String status;

    private Integer categoryId;

    private String categoryName;
}
