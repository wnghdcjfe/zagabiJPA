package com.example.zagabi.domain;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.ToString;

@Embeddable
@Data
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
