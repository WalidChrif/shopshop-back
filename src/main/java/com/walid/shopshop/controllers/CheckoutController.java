package com.walid.shopshop.controllers;

import com.walid.shopshop.dtos.Purchase;
import com.walid.shopshop.dtos.PurchaseResponse;
import com.walid.shopshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://localhost:4200", "http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return customerService.placeOrder(purchase);
    }


}
