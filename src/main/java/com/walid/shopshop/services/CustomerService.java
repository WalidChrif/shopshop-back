package com.walid.shopshop.services;

import com.walid.shopshop.dtos.Purchase;
import com.walid.shopshop.dtos.PurchaseResponse;
import com.walid.shopshop.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CustomerService {

    PurchaseResponse placeOrder(Purchase purchase);
    Page<Customer> getAllCustomers(Pageable pageable);

    List<Customer> getRecentCustomers();
}
