package com.codegym.thi_md4.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productTypeId;

    @Column(nullable = false)
    private String nameType;

    @OneToMany(mappedBy = "productType")
    private List<Product> products;
}