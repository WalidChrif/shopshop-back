package com.walid.shopshop.services;

import com.walid.shopshop.entities.Order;
import com.walid.shopshop.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Override
    public List<Order> findOrdersByCustomerEmail(String email, Pageable pageable) {
        return orderRepo.findByCustomerEmail(email, pageable);
    }

    @Override
    public Order findOrderByTrackingNumber(String trackingNumber) {
        return orderRepo.findByTrackingNumber(trackingNumber);
    }

    @Override
    public List<Order> findRecentOrders() {
        return orderRepo.findTop5ByOrderByDateCreatedDesc();
    }
}
