package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Customer;
import com.walid.shopshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://localhost:4200", "http://localhost:4200"})
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    Page<Customer> getCustomers(@RequestParam("page") int page, @RequestParam("size") int size
            , @RequestParam(defaultValue = "dateCreated") String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        return customerService.getAllCustomers(pageable);
    }
}
