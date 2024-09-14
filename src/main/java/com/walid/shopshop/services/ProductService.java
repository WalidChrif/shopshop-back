package com.walid.shopshop.services;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {

    List<Category> findAllCategories();

    Product findProductBySku(String sku);

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    Page<Product> findProductsByName(String name, Pageable pageable);

    List<Product> findOneProductPerCategory();
}
