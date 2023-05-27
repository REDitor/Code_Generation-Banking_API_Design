Feature: Transactions

  #Transaction creation
  Scenario: Create a transaction
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000001" and to "NL01INHO0000000002"
    When I call the post transaction endpoint
    Then I receive status code 201

  Scenario: Create a transaction with an invalid input data/object
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "11.23" and from "NonExistentIBAN" and to "NonExistentIBAN2"
    When I call the post transaction endpoint
    Then I receive status code 400

  Scenario: Create a transaction with an invalid token
    Given I have an invalid token
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000001" and to "NL01INHO0000000002"
    When I call the post transaction endpoint
    Then I receive status code 403

  #Fetching transactions
  Scenario: Get all transactions by IBAN
    Given I have a valid token for role "admin"
    When I call the get all transactions endpoint with iban "NL01INHO0000000002" and dateTimeFrom "25-04-2023 00:00" and dateTimeTo "25-07-2023 00:00"
    Then I receive status code 200

  Scenario: Get all transactions by IBAN with an invalid token
    Given I have an invalid token
    And I call the get all transactions endpoint with iban "NL01INHO0000000002" and dateTimeFrom "25-04-2023 00:00" and dateTimeTo "25-07-2023 00:00"
    Then I receive status code 403

  Scenario: Get all transactions for all accounts owned by user
    Given I have a valid token for role "user" or role "admin"
    When I call the get all transactions endpoint with userId "0c873bc6-6ab2-4803-8571-5afd3c1bc9e3" and dateTimeFrom "25-04-2023 00:00" and dateTimeTo "25-07-2023 00:00"
    Then I receive status code 200
#
  Scenario: Get all transactions for all accounts owned by user with an invalid token
    Given I have an invalid token
    When I call the get all transactions endpoint with userId "0c873bc6-6ab2-4803-8571-5afd3c1bc9e3" and dateTimeFrom "25-04-2023 00:00" and dateTimeTo "25-07-2023 00:00"
    Then I receive status code 403

  #Deposit
  Scenario: Deposit to selected account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000002"
    When I call the post deposit endpoint
    Then I receive status code 201

  Scenario: Deposit to account with a different owner
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000001"
    When I call the post deposit endpoint
    Then I receive status code 401

  Scenario: Deposit with an invalid token
    Given I have an invalid token
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000002"
    When I call the post deposit endpoint
    Then I receive status code 403

  Scenario: Deposit directly to a savings account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000004"
    When I call the post deposit endpoint
    Then I receive status code 403

  Scenario: Deposit to non existent IBAN
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000005"
    When I call the post deposit endpoint
    Then I receive status code 404

  #Withdraw
  Scenario: Withdraw from selected account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000002"
    When I call the post withdraw endpoint
    Then I receive status code 201

  Scenario: Withdraw from account with different owner
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000001"
    When I call the post withdraw endpoint
    Then I receive status code 401

  Scenario: Withdraw with an invalid token
    Given I have an invalid token
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000002"
    When I call the post withdraw endpoint
    Then I receive status code 403

  Scenario: Withdraw directly from savings account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000004"
    When I call the post deposit endpoint
    Then I receive status code 403

  Scenario: Withdraw from non existent IBAN
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000005"
    When I call the post withdraw endpoint
    Then I receive status code 404