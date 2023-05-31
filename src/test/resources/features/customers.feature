Feature: Everything related to customers

## Get all customers (user + admin)
  Scenario: Fetching all customers as Admin gives me a list
    Given I have an valid JWT token for role "admin"
    When Fetching all the customers
    Then There should be at least 2 results

  Scenario: Fetching all customers as User gives me an error
    Given I have an valid JWT token for role "user"
    When Fetching all the customers
    Then the response status codes should be 403

## Get customer by ID (user + admin)
  Scenario: Fetching a customer by ID as Admin returns a result
    Given I have an valid JWT token for role "admin"
    When Fetching all the customers
    When Fetching a random specific user
    Then There should be at least an object

  Scenario: Fetching a customer by ID as User returns an error
    Given I have an valid JWT token for role "admin"
    When Fetching all the customers
    Given I have an valid JWT token for role "user"
    When Fetching a random specific user
    Then the response status codes should be 401

## Create new customer (user + admin)
  Scenario: Creating a new customer return status OK
    Given I have an valid JWT token for role "admin"
    When I make a post request to create new customer with the following username "test_account_1234"
    Then the response status codes should be 201

  Scenario: Creating a new customer with wrong parameters returns 400
    Given I have an valid JWT token for role "admin"
    When I make a post request to create new customer with wrong parameters
    Then the response status codes should be 400

  Scenario: Creating a new customer return status OK
    Given I have an valid JWT token for role "user"
    When I make a post request to create new customer with the following username "test_account_09876"
    Then the response status codes should be 201

## Update customer by ID (user + admin)
  Scenario: Updating customer returns status OK
    Given I have an valid JWT token for role "admin"
    When Fetching all the customers
    And Updating a random customer's street name to "newStreetNameForMe"
    Then the response should contain the new street name of the customer as "newStreetNameForMe"

  Scenario: Updating customer returns status Permission denied
    Given I have an valid JWT token for role "admin"
    When Fetching all the customers
    Given I have an valid JWT token for role "user"
    And Updating a random customer's street name to "newStreetNameForMe"
    Then the response status codes should be 403

