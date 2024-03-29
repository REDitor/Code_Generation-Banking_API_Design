Feature: Transactions

  #Transaction creation
  Scenario: Create a transaction
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    When I call the post transaction endpoint
    Then I receive status code 201
    And I receive a response body with a transaction object

  Scenario: Create a transaction with an invalid input data/object
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "11.23" and from "NonExistentIBAN" and to "NonExistentIBAN2"
    When I call the post transaction endpoint
    Then I receive status code 400

  Scenario: Create a transaction with an invalid token
    Given I have an invalid token
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    When I call the post transaction endpoint
    Then I receive status code 403

  Scenario: Create a transaction from someone else's account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000004" and to "NL01INHO0000000002"
    When I call the post transaction endpoint
    Then I receive status code 403

  Scenario: Create a transaction from someone else's savings account
    Given I have a valid token for role "thief"
    And I have a transaction object with amount "11.23" and from "NL01INHO0000000003" and to "NL01INHO0000000001"
    When I call the post transaction endpoint
    Then I receive status code 403

  Scenario: Create a Transaction with a negative amount
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "-5.00" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    When I call the post transaction endpoint
    Then I receive status code 406

  Scenario: Create a transaction which exceeds account balance
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "1001" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    When I call the post transaction endpoint
    Then I receive status code 406

  Scenario: Create a transaction which exceeds transaction limit
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "501" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    When I call the post transaction endpoint
    Then I receive status code 406

  Scenario: Create a multiple transactions, exceeding the daily limit
    Given I have a valid token for role "user" or role "admin"
    And I have a transaction object with amount "500" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    When I call the post transaction endpoint
    Then I receive status code 201
    Then I have a transaction object with amount "500" and from "NL01INHO0000000002" and to "NL01INHO0000000004"
    And I call the post transaction endpoint
    Then I receive status code 406

  #Fetching transactions
  Scenario: Get all transactions by IBAN
    Given I have a valid token for role "admin"
    When I call the get all transactions endpoint with iban "NL01INHO0000000002" and dateTimeFrom "25-04-2023 00:00" and dateTimeTo "25-07-2023 00:00"
    Then I receive status code 200
    And I receive a response body with at least 2 transaction objects

  Scenario: Get all transactions by IBAN with an invalid token
    Given I have an invalid token
    And I call the get all transactions endpoint with iban "NL01INHO0000000002" and dateTimeFrom "25-04-2023 00:00" and dateTimeTo "25-07-2023 00:00"
    Then I receive status code 403

  #Deposit
  Scenario: Deposit to selected account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000002"
    When I call the post deposit endpoint
    Then I receive status code 201
    And I receive a response body with a transaction object

  Scenario: Deposit to account with a different owner
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000004"
    When I call the post deposit endpoint
    Then I receive status code 401

  Scenario: Deposit with an invalid token
    Given I have an invalid token
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000002"
    When I call the post deposit endpoint
    Then I receive status code 403

  Scenario: Deposit directly to a savings account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000003"
    When I call the post deposit endpoint
    Then I receive status code 403

  Scenario: Deposit to non existent IBAN
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000012345"
    When I call the post deposit endpoint
    Then I receive status code 404

  Scenario: Deposit to someone else's account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" and to "NL01INHO0000000004"
    When I call the post deposit endpoint
    Then I receive status code 401

  Scenario: Deposit a negative amount
    Given I have a valid token for role "user"
    And I have a transaction object with amount "-5.00" and to "NL01INHO0000000002"
    When I call the post deposit endpoint
    Then I receive status code 406

  #Withdraw
  Scenario: Withdraw from selected account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000002"
    When I call the post withdraw endpoint
    Then I receive status code 201
    And I receive a response body with a transaction object

  Scenario: Withdraw from account with different owner
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000004"
    When I call the post withdraw endpoint
    Then I receive status code 401

  Scenario: Withdraw with an invalid token
    Given I have an invalid token
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000002"
    When I call the post withdraw endpoint
    Then I receive status code 403

  Scenario: Withdraw directly from savings account
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000000003"
    When I call the post deposit endpoint
    Then I receive status code 403

  Scenario: Withdraw from non existent IBAN
    Given I have a valid token for role "user"
    And I have a transaction object with amount "11.23" (higher or equal to account balance) and from "NL01INHO0000012345"
    When I call the post withdraw endpoint
    Then I receive status code 404