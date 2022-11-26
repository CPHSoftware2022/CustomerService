package com.example.demo.integration;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.integration.testCRUDfunctionality.SpringIntegrationTest;
import com.example.demo.integration.test_models.CustomerTestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
public class StepDefinitionsGetCustomer extends SpringIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDTOAssembler customerDTOAssembler;

    @When("the client calls \\/api\\/customer\\/{int}")
    public void theClientCallsApiCustomer(int int1) throws Throwable{
        executeGet("http://localhost:8080/api/customer/"+int1);
    }

    @Then("the client receives status code of {int}")
    public void the_client_receives_status_code_of(Integer statusCode) throws Throwable{
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @Then("the client receives a Customer with id {int}")
    public void the_client_receives_a_customer_with_id(Integer int1) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CustomerTestModel customer = mapper.readValue(latestResponse.getBody(), CustomerTestModel.class);
            assertThat(customer.getId().intValue(), is(int1));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
