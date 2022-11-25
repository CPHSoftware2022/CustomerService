package com.example.demo.assemblers;

import com.example.demo.controller.CustomerController;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.CustomerEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class CustomerDTOAssembler extends RepresentationModelAssemblerSupport<CustomerEntity, CustomerDTO> {

    public CustomerDTOAssembler() {
        super(CustomerController.class, CustomerDTO.class);
    }

    @Override
    public CustomerDTO toModel(CustomerEntity entity)
    {
        CustomerDTO customerDTO = instantiateModel(entity);

        customerDTO.add(linkTo(
                methodOn(CustomerController.class)
                        .getCustomerById(entity.getId()))
                .withSelfRel());

        customerDTO.setId(entity.getId());
        customerDTO.setName(entity.getName());
        customerDTO.setEmail(entity.getEmail());
        customerDTO.setPhone(entity.getPhone());
        customerDTO.setAddress(entity.getAddress());
        return customerDTO;
    }

    @Override
    public CollectionModel<CustomerDTO> toCollectionModel(Iterable<? extends CustomerEntity> entities)
    {
        CollectionModel<CustomerDTO> customerDTOS = super.toCollectionModel(entities);

        customerDTOS.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());

        return customerDTOS;
    }


}
