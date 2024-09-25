package com.walid.shopshop.services;

import com.walid.shopshop.entities.Order;
import com.walid.shopshop.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public List<Order> findOrdersByCustomerEmail(String email) {
        return orderRepo.findByCustomerEmail(email);
    }
}
