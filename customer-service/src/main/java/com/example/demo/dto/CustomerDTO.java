package com.example.demo.dto;

import com.example.demo.entity.AddressEntity;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CustomerDTO extends RepresentationModel<CustomerDTO> {
    private Long id;
    private String name;
    private String email;
    private String phone;

    private AddressEntity address;

    public CustomerDTO(Long id, String name, String email, String phone, AddressEntity address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public CustomerDTO() {
    }
}
