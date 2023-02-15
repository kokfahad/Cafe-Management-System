package com.fahad.cafeteria.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "bill")
public class Bill implements Serializable {
    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total")
    private Integer totalAmount;

    @Column(name = "product_details", columnDefinition = "json")
    private String productDetails;

    @Column(name = "create_by")
    private String createdBy;

}
