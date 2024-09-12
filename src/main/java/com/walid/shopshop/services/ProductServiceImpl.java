package com.walid.shopshop.services;

import com.walid.shopshop.entities.Product;
import com.walid.shopshop.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Override
    public Page<Product> findProductsByName(String name, Pageable pageable) {
        return productRepo.findProductsByNameContaining(name, pageable);
    }

    @Override
    public Product findProductBySku(String sku) {
        return productRepo.findBySku(sku).orElseThrow();
    }

    @Override
    public Page<Product> findByCategoryId(Long id, Pageable pageable) {
        return productRepo.findByCategoryId(id, pageable);
    }

    @Override
    public List<Product> findOneProductPerCategory() {
        return productRepo.findOneProductPerCategory();
    }


}
