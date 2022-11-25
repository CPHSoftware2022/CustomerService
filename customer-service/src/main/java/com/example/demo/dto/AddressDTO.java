package com.example.demo.dto;

import org.springframework.hateoas.RepresentationModel;

public class AddressDTO extends RepresentationModel<AddressDTO> {
    private Long id;
    private String street;
    private String city;
    private String postalCode;
}
