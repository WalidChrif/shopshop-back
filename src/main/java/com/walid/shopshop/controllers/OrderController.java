package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Order;
import com.walid.shopshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://localhost:4200", "http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getOrdersByCustomerEmail(@RequestParam String email) {
        return orderService.findOrdersByCustomerEmail(email);
    }
    @GetMapping("/{trackingNumber}")
    public Order getOrdersByTrackingNumber(@PathVariable String trackingNumber) {
        return orderService.findOrderByTrackingNumber(trackingNumber);
    }

    @GetMapping("/all")
    public Page<Order> getAllOrders(@RequestParam int page, @RequestParam int size
            , @RequestParam(defaultValue = "dateCreated") String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        return orderService.findAllOrders(pageable);
    }


}
