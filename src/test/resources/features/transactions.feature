Feature: Everything related to transactions

  #Transaction creation
  Scenario: Create a transaction
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000001" and to "NL01INHO0000000002"
    When I call the post transaction endpoint
    Then I receive status code 201 for creating a transaction

  Scenario: Create a transaction with invalid input data/object
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "11.23" and from "NL03

#TODO: Create transaction with invalid input
#TODO: Create transaction with invalid token

  #Fetching transactions
  Scenario: Getting transactions

#TODO: Get all transactions with valid token
#TODO: Get all transactions with invalid token