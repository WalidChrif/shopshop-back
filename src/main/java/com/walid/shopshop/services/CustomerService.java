package com.walid.shopshop.services;

import com.walid.shopshop.dtos.Purchase;
import com.walid.shopshop.dtos.PurchaseResponse;

public interface CustomerService {

    PurchaseResponse placeOrder(Purchase purchase);

}
