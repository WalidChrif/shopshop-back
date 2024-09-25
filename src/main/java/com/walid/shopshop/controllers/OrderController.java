package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Order;
import com.walid.shopshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getOrdersByCustomerEmail(@RequestParam String email) {
        return orderService.findOrdersByCustomerEmail(email);
    }


}
