package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Product;
import com.walid.shopshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/category/{id}")
    public Page<Product> findProductByCategory(
            @PathVariable Long id
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue = "name") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy, "unitPrice").ascending());
        return productService.findByCategoryId(id, pageable);
    }

    @GetMapping("/search/{name}")
    public Page<Product> findProductsByName(
            @PathVariable String name
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue = "name") String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        return productService.findProductsByName(name, pageable);
    }

    @GetMapping("/sku/{sku}")
    public Product findProductBySku(@PathVariable String sku) {
        return productService.findProductBySku(sku);
    }

    @GetMapping()
    public List<Product> findOneProductPerCategory(){
        return productService.findOneProductPerCategory();
    }

}
