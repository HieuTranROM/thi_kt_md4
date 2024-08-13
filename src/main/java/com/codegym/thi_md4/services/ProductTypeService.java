package com.codegym.thi_md4.services;

import com.codegym.thi_md4.models.ProductType;
import com.codegym.thi_md4.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    public List<ProductType> findAll() {
        return productTypeRepository.findAll();
    }

    public ProductType findById(Long id) {
        return productTypeRepository.findById(id).orElse(null);
    }

    public ProductType save(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    public void deleteById(Long id) {
        productTypeRepository.deleteById(id);
    }
}