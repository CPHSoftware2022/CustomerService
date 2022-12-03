package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 100)
    private String street;
    @Column(nullable = false, length = 100)
    private String city;
    @Column(name = "postal_code", nullable = false)
    private int postalCode;

    public AddressEntity() {
    }

    public AddressEntity(Long id, String street, String city, int postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}