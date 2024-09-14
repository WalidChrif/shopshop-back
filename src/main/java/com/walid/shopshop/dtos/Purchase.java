package com.walid.shopshop.dtos;

import com.walid.shopshop.entities.Address;
import com.walid.shopshop.entities.Customer;
import com.walid.shopshop.entities.Order;
import com.walid.shopshop.entities.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class Purchase {

    private String trackingNumber;
    private Customer customer;
    private Order order;
    private Address shippingAddress;
    private Address billingAddress;
    private List<OrderItem> orderItems;


}
