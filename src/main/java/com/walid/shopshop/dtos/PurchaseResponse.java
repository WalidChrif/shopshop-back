package com.walid.shopshop.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class PurchaseResponse {
    @NonNull
    private String trackingNumber;

}
