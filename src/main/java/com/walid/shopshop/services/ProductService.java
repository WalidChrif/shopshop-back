package com.walid.shopshop.services;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    List<Product> findPopularProducts();

    Page<Product> findAllProducts(Pageable pageable);

    List<Category> findAllCategories();

    Product findProductBySku(String sku);

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    Page<Product> findProductsByName(String name, Pageable pageable);

    List<Product> findOneProductPerCategory();

    Product bestSeller();

    void reduceStock(String sku, int quantity);

    void addSales(String sku, int quantity);

    Product addProduct(MultipartFile image, String name, String description, String unitPrice
            , String unitsInStock, boolean active, Long categoryId);

    List<Product> findRecentProducts();

    void deleteProduct(String sku);
}
