Feature: Everything related to accounts

  Scenario: Getting accounts of a user is Status OK
    Given I have an valid token for role "admin" to access accounts
    When I call get accounts by IBAN "NL21INHO0123400081"
    Then I receive a status code of 200
