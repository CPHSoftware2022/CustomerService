package com.example.demo.integration;

import com.example.demo.assemblers.CustomerDTOAssembler;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.integration.testCRUDfunctionality.SpringIntegrationTest;
import com.example.demo.integration.test_models.CustomerListTestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class StepDefinitionsGetAllCustomers extends SpringIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDTOAssembler customerDTOAssembler;

    @When("the client calls \\/api\\/customers")
    public void the_client_calls_api_customers() throws IOException {
        executeGet("http://localhost:8080/api/customers");
    }

    @Then("the client receives status code of {int} - 2")
    public void the_client_receives_status_code_of2(Integer statusCode) throws Throwable{
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @Then("the client receives a list of Customers with length {int}")
    public void the_client_receives_a_list_of_customers_with_length(Integer int1) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CustomerListTestModel customerListTestModel = mapper.readValue(latestResponse.getBody(), CustomerListTestModel.class);
        int listSize = customerListTestModel.get_embedded().getCustomerDToes().size();
        assertThat(listSize, is(int1));
    }

}
