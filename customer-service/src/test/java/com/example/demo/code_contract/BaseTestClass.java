package com.example.demo.code_contract;

import com.example.demo.CustomerServiceApplication;
import com.example.demo.controller.CustomerController;
import com.example.demo.entity.AddressEntity;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.Optional;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;

@SpringBootTest(classes = CustomerServiceApplication.class)
public abstract class BaseTestClass {

    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(customerController);

        Mockito.when(customerRepository.findById(20L))
                .thenReturn(Optional.of(new CustomerEntity(20L, "Rodd Rosenkranc", "x8VXQeEYX4c", "rrosenkrancj@edublogs.org", "+86 783 433 1537", new AddressEntity(110L, "80187 Kunze Heights", "Eulahland", 320736158))));
    }
}