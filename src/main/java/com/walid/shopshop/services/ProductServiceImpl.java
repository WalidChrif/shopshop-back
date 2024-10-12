package com.walid.shopshop.services;

import com.walid.shopshop.entities.Category;
import com.walid.shopshop.entities.Product;
import com.walid.shopshop.repos.CategoryRepo;
import com.walid.shopshop.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;

    private static final String ASSETS_DIR = "../shopshop-front/src/assets/images/products/";
//    private static final String ImageUrl = "/assets/images/products/";

    @Override
    public Product bestSeller() {
        return productRepo.findTopByOrderBySalesDesc();
    }

    @Override
    public List<Product> findPopularProducts() {
        return productRepo.findTop5ByOrderBySalesDesc();
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

    @Transactional
    @Override
    public Product addProduct(MultipartFile image, String name, String description
            , String unitPrice, String unitsInStock, boolean active, Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow();
        String sku = generateSku(category.getName());
        Path categoryPath = Path.of(ASSETS_DIR + category.getName().toLowerCase());
        if (!Files.exists(categoryPath)) {
            try {
                Files.createDirectories(categoryPath);
            } catch (IOException e) {
                throw new RuntimeException("Could not create directory");
            }
        }
//        String imageName = image.getOriginalFilename();
        String[] skuParts = sku.split("-");
        Path imagePath = categoryPath.resolve(skuParts[0].toLowerCase() + "-shopshop-" + skuParts[1] + ".png");
        try {
            Files.copy(image.getInputStream(), imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not save image");
        }
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setUnitPrice(BigDecimal.valueOf(Double.parseDouble(unitPrice)));
        product.setUnitsInStock(Integer.parseInt(unitsInStock));
        product.setActive(active);
        product.setCategory(category);
        product.setSku(sku);
        String imagePathString = imagePath.toString();
//        product.setImageUrl(imagePathString.substring(imagePathString.indexOf("assets")).replaceAll("\\\\", "/"));
        int assetsIndex = imagePathString.indexOf("assets");
        String urlPath = imagePathString.substring(assetsIndex).replace("\\", "/");
        product.setImageUrl(urlPath);

        return productRepo.save(product);
    }

    @Override
    public List<Product> findRecentProducts() {
        return productRepo.findTop5ByOrderByDateCreatedDesc();
    }

    @Override
    public void deleteProduct(String sku) {
        Product product = findProductBySku(sku);
        Path imagePath = Path.of("..\\shopshop-front\\src\\" + product.getImageUrl());
        System.out.println(imagePath);
        if (!Files.exists(imagePath)) {
            System.out.println("Image does not exist");
        } else try {
            Files.delete(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete image");
        }
        productRepo.deleteById(product.getId());
    }

    private String generateSku(String category) {
        return category.toUpperCase().substring(0, category.length() - 1) + "-" + (int) ((Math.random() * 100) + 2000);
    }


}
