package com.walid.shopshop.services;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    CategoryRepo repo;

    @Override
    public List<Category> findAllCategories() {
        return repo.findAll();
    }

}
