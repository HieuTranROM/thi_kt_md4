package com.codegym.thi_md4.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String nameProduct;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "productTypeId", nullable = false)
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;
}