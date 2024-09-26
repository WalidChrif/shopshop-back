package com.walid.shopshop.repos;

import com.walid.shopshop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByCustomerEmailOrderByDateCreatedDesc(String email);

    Order findByTrackingNumber(String trackingNumber);
}
