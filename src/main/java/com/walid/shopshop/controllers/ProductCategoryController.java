package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping()
    public List<Category> findAllProductsCategories(){
        return productCategoryService.findAllCategories();
    }

}
