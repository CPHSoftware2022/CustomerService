package com.example.demo.integration;

import com.example.demo.integration.test_models.CustomerListTestModel;
import com.example.demo.integration.test_models.CustomerTestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@DataJpaTest
public class StepDefinitionsCustomer {

    WireMockServer wireMockServer = new WireMockServer(8081);
    private HttpResponse latestHttpResponse = null;


    @When("the client calls \\/api\\/customer\\/{int}")
    public void theClientCallsApiCustomer(int int1) throws Throwable{

        latestHttpResponse = null;
        wireMockServer.start();

        configureFor("localhost", 8081);
        stubFor(get(urlEqualTo("/api/customer/"+int1)).willReturn(aResponse().withBody(
                "{\n" +
                        "    \"id\": 20,\n" +
                        "    \"name\": \"Rodd Rosenkranc\",\n" +
                        "    \"email\": \"rrosenkrancj@edublogs.org\",\n" +
                        "    \"phone\": \"+86 783 433 1537\",\n" +
                        "    \"address\": {\n" +
                        "        \"id\": 110\n" +
                        "    },\n" +
                        "    \"_links\": {\n" +
                        "        \"self\": {\n" +
                        "            \"href\": \"http://localhost:8080/api/customer/20\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"
        )));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:8081/api/customer/"+int1);
        HttpResponse httpResponse = httpClient.execute(request);

        latestHttpResponse = httpResponse;

        verify(exactly(1),getRequestedFor(urlEqualTo("/api/customer/"+int1)));
        wireMockServer.stop();
    }

    @Then("the client receives status code of {int}")
    public void the_client_receives_status_code_of(Integer statusCode) throws Throwable{
        Integer currentStatusCode = latestHttpResponse.getStatusLine().getStatusCode();
        assertEquals(statusCode, currentStatusCode);
    }

    @Then("the client receives a Customer with id {int}")
    public void the_client_receives_a_customer_with_id(Integer int1) throws IOException {
        String json = EntityUtils.toString(latestHttpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        try {
            CustomerTestModel customer = mapper.readValue(json, CustomerTestModel.class);
            assertThat(customer.getId().intValue(), is(int1));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @When("the client calls \\/api\\/customers")
    public void the_client_calls_api_customers() throws IOException {
        latestHttpResponse = null;
        wireMockServer.start();

        configureFor("localhost", 8081);
        stubFor(get(urlEqualTo("/api/customers")).willReturn(aResponse().withBody(
                "{\n" +
                        "    \"_embedded\": {\n" +
                        "        \"customerDToes\": [\n" +
                        "            {\n" +
                        "                \"id\": 1,\n" +
                        "                \"name\": \"Evelin Hartfleet\",\n" +
                        "                \"email\": \"ehartfleet0@china.com.cn\",\n" +
                        "                \"phone\": \"+7 356 123 2844\",\n" +
                        "                \"address\": {\n" +
                        "                    \"id\": 13\n" +
                        "                },\n" +
                        "                \"_links\": {\n" +
                        "                    \"self\": {\n" +
                        "                        \"href\": \"http://localhost:8080/api/customer/1\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"id\": 2,\n" +
                        "                \"name\": \"Judie Bizley\",\n" +
                        "                \"email\": \"jbizley1@de.vu\",\n" +
                        "                \"phone\": \"+355 139 199 5831\",\n" +
                        "                \"address\": {\n" +
                        "                    \"id\": 11\n" +
                        "                },\n" +
                        "                \"_links\": {\n" +
                        "                    \"self\": {\n" +
                        "                        \"href\": \"http://localhost:8080/api/customer/2\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"_links\": {\n" +
                        "        \"self\": {\n" +
                        "            \"href\": \"http://localhost:8080/api/customers\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"
        )));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:8081/api/customers");
        HttpResponse httpResponse = httpClient.execute(request);

        latestHttpResponse = httpResponse;

        verify(exactly(1),getRequestedFor(urlEqualTo("/api/customers")));
        wireMockServer.stop();

    }

    @Then("the client receives a list of Customers with length {int}")
    public void the_client_receives_a_list_of_customers_with_length(Integer int1) throws IOException {
        String json = EntityUtils.toString(latestHttpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        CustomerListTestModel customerListTestModel = mapper.readValue(json, CustomerListTestModel.class);
        int listSize = customerListTestModel.get_embedded().getCustomerDToes().size();
        assertThat(listSize, is(int1));
    }


}
