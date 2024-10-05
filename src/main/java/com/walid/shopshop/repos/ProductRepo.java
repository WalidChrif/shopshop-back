package com.walid.shopshop.repos;

import com.walid.shopshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findTopByOrderBySalesDesc();

    List<Product> findTop5ByOrderBySalesDesc();

    Optional<Product> findBySku(String sku);

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    Page<Product> findProductsByNameContaining(String name, Pageable pageable);

    @Query(value = "SELECT p.* FROM product p " +
            "JOIN (SELECT MIN(id) as id, category_id FROM product GROUP BY category_id) grouped " +
            "ON p.id = grouped.id", nativeQuery = true)
    List<Product> findOneProductPerCategory();

    List<Product> findTop5ByOrderByDateCreatedDesc();
}
