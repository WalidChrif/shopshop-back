package com.walid.shopshop.repos;

import com.walid.shopshop.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    List<Customer> findTop5ByOrderByDateCreatedDesc();
}
