package com.example.demo.controller;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private CustomerDTOAssembler customerDTOAssembler;

    @GetMapping("/test")
    String test() {
        return "test";
    }

    @GetMapping("/api/customers")
    public ResponseEntity<CollectionModel<CustomerDTO>> getAllCustomers()
    {
        List<CustomerEntity> customerEntities = (List<CustomerEntity>) customerRepository.findAll();

        return new ResponseEntity<>(
                customerDTOAssembler.toCollectionModel(customerEntities),
                HttpStatus.OK);
    }

    @GetMapping("/api/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id)
    {
        return customerRepository.findById(id)
                .map(customerDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
