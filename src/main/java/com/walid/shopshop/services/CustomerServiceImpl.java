package com.walid.shopshop.services;

import com.walid.shopshop.dtos.Purchase;
import com.walid.shopshop.dtos.PurchaseResponse;
import com.walid.shopshop.entities.Customer;
import com.walid.shopshop.entities.Order;
import com.walid.shopshop.entities.OrderItem;
import com.walid.shopshop.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepo customerRepo;


    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        order.setTrackingNumber(generateTrackingNumber());
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        List<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(order);
            order.addOrderItem(orderItem);
        });
        Customer customer = purchase.getCustomer();
        order.setCustomer(customer);
        customer.addOrder(order);
        customerRepo.save(customer);
        return new PurchaseResponse(order.getTrackingNumber());
    }

    private String generateTrackingNumber() {
       return UUID.randomUUID().toString();
    }


}
