package com.walid.shopshop.services;

import com.walid.shopshop.entities.Category;

import java.util.List;

//@Service
public interface ProductCategoryService {

    List<Category> findAllCategories();

}
