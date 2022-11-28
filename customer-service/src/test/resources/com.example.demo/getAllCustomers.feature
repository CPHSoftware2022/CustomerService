Feature: Get all Customers
  Scenario: client makes call to GET /api/customers
    When the client calls /api/customers
    Then the client receives status code of 200
    And the client receives a list of Customers with length 2