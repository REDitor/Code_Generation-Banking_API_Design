Feature: Everything related to accounts

  Scenario: Getting accounts of a user is Status OK
    Given I have an valid token for role "admin" to access accounts
    When I call get accounts by IBAN "NL01INHO0000000003"
    Then the response status code should be 200

  Scenario: Create an account
    Given I have an valid token for role "admin" to access accounts
    Given the following new account details:
      | Type  | Status | MinimumBalance | UserID
      | ACCOUNT_TYPE_CURRENT | Open | 0 | 3
    And i get a user without an account
    When i create the account
    Then the response status code should be 201



