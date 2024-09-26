package com.walid.shopshop.dtos;

import com.walid.shopshop.entities.Address;
import com.walid.shopshop.entities.Customer;
import com.walid.shopshop.entities.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Purchase {

    private String trackingNumber;
    private Customer customer;
    private BigDecimal totalPrice;
    private int totalItems;
    private BigDecimal shippingCost;
    private Address shippingAddress;
    private Address billingAddress;
    private List<OrderItem> orderItems;


}
