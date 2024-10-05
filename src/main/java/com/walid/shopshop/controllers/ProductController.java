package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.entities.Product;
import com.walid.shopshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"https://localhost:4200", "http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add-product")
    public Product addProduct(@RequestParam MultipartFile image, @RequestParam String name, @RequestParam String description
            , @RequestParam String unitPrice, @RequestParam String unitsInStock, @RequestParam boolean active
            , @RequestParam Long categoryId) {
        return productService.addProduct(image, name, description, unitPrice, unitsInStock, active, categoryId);
    }
    @GetMapping("/popular")
    public List<Product> findPopularProducts() {
        return productService.findPopularProducts();
    }

    @GetMapping("/best-seller")
    public Product bestSeller() {
        return productService.bestSeller();
    }

    @GetMapping("/categories")
    public List<Category> findAllProductsCategories() {
        return productService.findAllCategories();
    }

    @GetMapping("/category/{id}")
    public Page<Product> findProductByCategory(
            @PathVariable Long id
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue = "name,asc") String orderBy
    ) {
        String[] sortParams = orderBy.split(",");
        Sort sorting = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sorting);
        return productService.findByCategoryId(id, pageable);
    }

    @GetMapping("/search/{name}")
    public Page<Product> findProductsByName(
            @PathVariable String name
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue = "name,asc") String orderBy
    ) {
        String[] sortParams = orderBy.split(",");
        Sort sorting = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sorting);
        return productService.findProductsByName(name, pageable);
    }

    @GetMapping("/sku/{sku}")
    public Product findProductBySku(@PathVariable String sku) {
        return productService.findProductBySku(sku);
    }

    @GetMapping("/all")
    public Page<Product> findAllProducts(
            @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue = "dateCreated,desc") String orderBy) {
        String[] sortParams = orderBy.split(",");
        Sort sorting = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        Pageable pageable = PageRequest.of(page, size, sorting);
        return productService.findAllProducts(pageable);
    }

    @GetMapping()
    public List<Product> findOnePerCategory() {
        return productService.findOneProductPerCategory();
    }
    @GetMapping("/recent")
    public List<Product> findRecentProducts() {
        return productService.findRecentProducts();
    }

}
