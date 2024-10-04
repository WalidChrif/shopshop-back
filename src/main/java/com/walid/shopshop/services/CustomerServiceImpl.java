package com.walid.shopshop.services;

import com.walid.shopshop.dtos.Purchase;
import com.walid.shopshop.dtos.PurchaseResponse;
import com.walid.shopshop.entities.Customer;
import com.walid.shopshop.entities.Order;
import com.walid.shopshop.entities.OrderItem;
import com.walid.shopshop.repos.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ProductService productService;

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = new Order();
        order.setTrackingNumber(generateTrackingNumber());
        order.setTotalPrice(purchase.getTotalPrice());
        order.setTotalItems(purchase.getTotalItems());
        order.setShippingCost(purchase.getShippingCost());
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());
        List<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(orderItem -> {
            productService.reduceStock(orderItem.getSku(), orderItem.getQuantity());
            productService.addSales(orderItem.getSku(), orderItem.getQuantity());
            orderItem.setQuantity(orderItem.getQuantity());
            orderItem.setOrder(order);
            order.addOrderItem(orderItem);
        });
        Customer customer = purchase.getCustomer();
        Customer customerByEmail = customerRepo.findByEmail(customer.getEmail());
        if (customerByEmail != null){
            customer = customerByEmail;
        }
        order.setCustomer(customer);
        customer.addOrder(order);
        customerRepo.save(customer);
        return new PurchaseResponse(order.getTrackingNumber());
    }

    private String generateTrackingNumber() {
       return UUID.randomUUID().toString();
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepo.findAll(pageable);
    }
}
