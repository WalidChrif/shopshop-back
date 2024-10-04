package com.walid.shopshop.repos;

import com.walid.shopshop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByCustomerEmailOrderByDateCreatedDesc(String email);

    Order findByTrackingNumber(String trackingNumber);

    @Query("SELECT SUM(oi.unitPrice * oi.quantity) FROM OrderItem oi")
    BigDecimal findTotalEarnings();
}
