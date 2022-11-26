Feature: Get a Customer
  Scenario: client makes call to GET /api/customer/20
    When the client calls /api/customer/20
    Then the client receives status code of 200
    And the client receives a Customer with id 20
