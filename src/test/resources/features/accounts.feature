Feature: Everything related to accounts

  Scenario: Getting accounts of a user is Status OK
    Given I have an valid token for role "admin" to access accounts
    When I call get accounts by IBAN "NL01INHO0000000003"
    Then the response status code should be 200

  Scenario: Create an account
    Given the following new account details:
      | IBAN | Type | Balance | Status | MinimumBalance |
      | NL01INHO0000007652 | ACCOUNT_TYPE_CURRENT | 0 | Open | 0 |
    When I create the account
    Then the response status code should be 201

  Scenario: Get an account
    Given the account with iban "NL01INHO0000000002"
    When I request the account details
    Then the response status code should be 200
    And the response should contain the following details:
      | iban | firstName | lastName | balance | status | type |

  Scenario: Get an account with invalid iban
    Given the account with iban "invalid"
    When I request the account details
    Then the response status code should be 400
    And the response should contain the error message "Bad request. Invalid request body."

  Scenario: Get an account with permission denied
    Given the account with iban "NL01INHO0000000001"
    When I request the account details as a non-employee
    Then the response status code should be 403
    And the response should contain the error message "Permission Denied: Cannot access master account"

  Scenario: Update an account
    Given the account with iban "NL01INHO0000000002"
    And the following account details to update:
      | minimumBalance | type      | status |
      | 500            | Checking | ACTIVE |
    When I update the account
    Then the response status code should be 200
    And the response should contain the following details:
      | iban | firstName | lastName | balance | status | type |

  Scenario: Update an account with invalid details
    Given the account with iban "NL01INHO0000000002"
    And the following invalid account details to update:
      | minimumBalance | type  | status |
      | -1             | ""    | ""     |
    When I update the account
    Then the response status code should be 400
    And the response should contain the error message "Bad request. Invalid request body."

  Scenario: Update an account with permission denied
    Given the account with iban "NL01INHO0000000002"
    And the following account details to update:
      | minimumBalance | type      | status |
      | 500            | Checking | ACTIVE |
    When I update the account as a non-owner and non-employee
    Then the response status code should be 403
    And the response should contain the error message "Permission Denied: You do not own this account or do not have employee permissions"


