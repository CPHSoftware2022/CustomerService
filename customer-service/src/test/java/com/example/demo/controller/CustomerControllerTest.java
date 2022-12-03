package com.example.demo.controller;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.controller.CustomerController;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerDTOAssembler customerDTOAssembler;
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;
    CustomerEntity customerEntity = new CustomerEntity();
    CustomerEntity customerEntity2 = new CustomerEntity();
    CustomerEntity customerEntityLargeId = new CustomerEntity();
    @BeforeEach
    void setUp() {
        AddressEntity address = new AddressEntity(500L, "This Street", "That City", 1111);
        AddressEntity address2 = new AddressEntity(4L, "792 Dusty Ports Suite 321", "Leoneburgh", 599846124);

        customerEntity.setId(20L);
        customerEntity.setName("Test Guy");
        customerEntity.setPassword("jnvfdkjbvkjbfkjewbfej");
        customerEntity.setEmail("tg@gmail.com");
        customerEntity.setPhone("+4555566677");
        customerEntity.setAddress(address);

        customerEntity2.setId(20L);
        customerEntity2.setName("Rodd Rosenkranc");
        customerEntity2.setPassword("jnvfdkjbvkjbfkjewbfej");
        customerEntity2.setEmail("rrosenkrancj@edublogs.org");
        customerEntity2.setPhone("+86 783 433 1537");
        customerEntity2.setAddress(address2);

        customerEntityLargeId.setId(4000L);
    }

    @Test
    void getAllCustomers() {
        int exp = 20;
        int act = customerController.getAllCustomers().getBody().getContent().size();
        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Testing if password has an impact on getting customer by id 20")
    void getCustomerById() {
        Optional<CustomerEntity> customerEntityOptional = Optional.of(customerEntity2);

        ResponseEntity<CustomerDTO> responseEntity = customerController.getReponseEntity(customerEntityOptional);

        String exp = responseEntity.toString();
        String act = customerController.getCustomerById(Long.valueOf(20)).toString();

        assertEquals(exp, act);
    }

    @Test
    @DisplayName("Testing getting customer by id 20 isn't equal to self made customer")
    void getCustomerById2() {
        Optional<CustomerEntity> customerEntityOptional = Optional.of(customerEntity);

        ResponseEntity<CustomerDTO> responseEntity = customerController.getReponseEntity(customerEntityOptional);

        String exp = responseEntity.toString();
        String act = customerController.getCustomerById(Long.valueOf(20)).toString();

        assertNotEquals(exp, act);
    }

    @Test
    @DisplayName("Testing sendKafkaMessage when customer entity doesn't exist")
    void sendKafkaMessage() {
        Optional<CustomerEntity> customerEntityOptional = Optional.of(customerEntityLargeId);
        ResponseEntity<CustomerDTO> responseEntity = customerController.getReponseEntity(customerEntityOptional);

        String exp = "<404 NOT_FOUND Not Found,[]>";
        String act = customerController.getCustomerById(customerEntityLargeId.getId()).toString();

        assertEquals(exp, act);
    }


    @Test
    @DisplayName("Testing getResponseEntity based on self made Optional Entity")
    void getReponseEntity() {
        Optional<CustomerEntity> customerEntityOptional = Optional.ofNullable(customerEntity);

        ResponseEntity<CustomerDTO> exp = customerEntityOptional
                .map(customerDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        ResponseEntity<CustomerDTO> act = customerController.getReponseEntity(customerEntityOptional);

        assertEquals(exp, act);
    }
    @Test
    @DisplayName("Testing getResponseEntity based on self made Optional Entity when changing name")
    void getReponseEntityNegative() {
        Optional<CustomerEntity> customerEntityOptional = Optional.ofNullable(customerEntity);

        ResponseEntity<CustomerDTO> exp = customerEntityOptional
                .map(customerDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        customerEntityOptional.get().setName("New name");

        ResponseEntity<CustomerDTO> act = customerController.getReponseEntity(customerEntityOptional);

        assertNotEquals(exp, act);
    }
}