import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return a customer with id 20"
    request{
        method GET()
        url("/api/customer/") {
            queryParameters {
                parameter("id", "20")
            }
        }
    }
    response {
        body("{\n" +
                "    \"id\": 20,\n" +
                "    \"name\": \"Rodd Rosenkranc\",\n" +
                "    \"email\": \"rrosenkrancj@edublogs.org\",\n" +
                "    \"phone\": \"+86 783 433 1537\",\n" +
                "    \"address\": {\n" +
                "        \"id\": 110,\n" +
                "        \"street\": \"80187 Kunze Heights\",\n" +
                "        \"city\": \"Eulahland\",\n" +
                "        \"postalCode\": 320736158\n" +
                "    },\n" +
                "    \"_links\": {\n" +
                "        \"self\": {\n" +
                "            \"href\": \"http://localhost:8080/api/customer/20\"\n" +
                "        }\n" +
                "    }\n" +
                "}")
        status 200
    }
}
