package com.walid.shopshop.services;

import com.walid.shopshop.entities.Order;

import java.util.List;

public interface OrderService {

    List<Order> findOrdersByCustomerEmail(String email);
    Order findOrderByTrackingNumber(String trackingNumber);
}
