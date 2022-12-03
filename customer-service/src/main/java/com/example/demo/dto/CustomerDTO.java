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


}
