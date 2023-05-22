Feature: Everything related to accounts

  #Account creation
  Scenario: Create an account (employee)
    Given the following new account details:
      | Type                 | Status | MinimumBalance |
      | ACCOUNT_TYPE_CURRENT | Open   | 0              |
    And i get a user without an account
    Given I have an valid token for role "admin"
    When i create the account
    Then the response status code should be 201


  Scenario: Create an account (customer)
    Given the following new account details:
      | Type                 | Status | MinimumBalance |
      | ACCOUNT_TYPE_CURRENT | Open   | 0              |
    And i get a user without an account
    Given I have an valid token for role "user"
    When i create the account
    Then the response status code should be 403

  Scenario: Create an account with invalid data
    Given the following new account details:
      | Type                 | Status | MinimumBalance |
      | ACCOUNT_TYPE_CURRENT | Open   | 0              |
    Given I have an valid token for role "admin"
    When i create the account
    Then the response status code should be 400

    #Fetching accounts
    ## By IBAN
    Scenario: Getting account by IBAN of a user is Status OK (admin)
      Given I have an valid token for role "admin"
      When I call get accounts by IBAN "NL01INHO0000000003"
      Then the response status code should be 200

    Scenario: Getting account by IBAN of a user is Status access denied (user)
      Given I have an valid token for role "user"
      When I call get accounts by IBAN "NL01INHO0000000003"
      Then the response status code should be 403

    Scenario: Getting own account is Status OK (user)
      Given I have an valid token for role "user"
      When I call get accounts by IBAN "NL01INHO0000000002"
      Then the response status code should be 200

    ## Fetch customers without accounts
    Scenario: Fetching customers without accounts is Status OK (admin)
      Given I have an valid token for role "admin"
      When I call get customers without accounts
      Then the response status code should be 200

    Scenario: Fetching customers without accounts is Status access denied (user)
      Given I have an valid token for role "user"
      When I call get customers without accounts
      Then the response status code should be 403

    ## Fetch accounts by name
    Scenario: Fetching accounts by name is Status OK  (admin)
      Given I have an valid token for role "admin"
      When Fetching accounts by the name "Sander"
      Then the response status code should be 200

    Scenario: Fetching accounts by name is Status Access denied  (user)
      Given I have an valid token for role "user"
      When Fetching accounts by the name "NoUserWithTHisName"
      Then the response status code should be 404

    ## Fetch totalBalance by userID
    Scenario: Fetching balance by UserID (admin)
      Given I have an valid token for role "admin"
      And i get a user without an account
      When I get the total balance using the UserID
      Then the balance amount should be 0.0

    Scenario: Fetching balance by UserID (user)
      And i get a user without an account
      Given I have an valid token for role "user"
      When I get the total balance using the UserID
      Then the response status code should be 401

    # Update accounts
    Scenario: Updating account using IBAN (admin)
      Given I have an valid token for role "admin"
      When I change the status of account "NL01INHO0000000002" to "Closed"
      Then the response should contain the new status of the account as "Closed"

    Scenario: Updating account using IBAN (user)
      Given I have an valid token for role "user"
      When I change the status of account "NL01INHO0000000002" to "Closed"
      Then the response status code should be 403
