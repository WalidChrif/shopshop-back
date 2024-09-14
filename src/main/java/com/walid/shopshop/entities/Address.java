package com.walid.shopshop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String country;
    private String state;
    private String street;
    private String zipcode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}
