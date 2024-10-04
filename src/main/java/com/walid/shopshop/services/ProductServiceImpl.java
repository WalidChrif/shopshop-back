package com.walid.shopshop.services;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.entities.Product;
import com.walid.shopshop.repos.CategoryRepo;
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
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public Product bestSeller() {
        if (productRepo.findAllByOrderByUnitsInStockDesc().isEmpty()) {
            return null;
        }
        return productRepo.findAllByOrderByUnitsInStockDesc().get(0);
    }

    @Override
    public Page<Product> findProductsByName(String name, Pageable pageable) {
        return productRepo.findProductsByNameContaining(name, pageable);
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
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

    @Override
    public void reduceStock(String sku, int quantity) {
        Product product = productRepo.findBySku(sku).orElseThrow();
        product.setUnitsInStock(product.getUnitsInStock() - quantity);
        productRepo.save(product);
    }

    @Override
    public void addSales(String sku, int quantity) {
        Product product = productRepo.findBySku(sku).orElseThrow();
        product.setSales(product.getSales() + quantity);
    }


}
