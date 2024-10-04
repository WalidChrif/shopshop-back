package com.walid.shopshop.services;

import com.walid.shopshop.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Page<Order> findAllOrders(Pageable pageable);
    List<Order> findOrdersByCustomerEmail(String email);
    Order findOrderByTrackingNumber(String trackingNumber);
}
