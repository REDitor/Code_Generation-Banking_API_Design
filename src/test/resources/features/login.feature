Feature: Everything related to login

  Scenario: Login with correct credentials returns JWT token
    When Login in with "BrunoMarques123" and "secret123"
    Then the response status should be 200
    Then I get a token


  Scenario: Login with invalid credentials returns error
    When Login in with "BrunoMarques123" and "secret1235432"
    Then the response status should be 403