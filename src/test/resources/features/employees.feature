Feature: Everything related to employees

  #Employee creation
  Scenario: Create an employee
    Given I have a valid authentication token for role "admin"
    And I have a new employee object with username "test_employee_1234"
    When I call the post new employee endpoint
    Then The status code is 201
    And I get a response with an employee object

  Scenario: Create an employee with wrong parameters / invalid request body
    Given I have a valid authentication token for role "admin"
    And I have a new employee object with wrong parameters
    When I call the post new employee endpoint
    Then The status code is 400

  Scenario: Create an employee with an invalid token
    Given I have an invalid authentication token
    And I have a new employee object with username "test_employee_1234"
    When I call the post new employee endpoint
    Then The status code is 403

  #Fetching employees
  Scenario: Get all employees
    Given I have a valid authentication token for role "admin"
    When I call the get all employees endpoint
    Then The status code is 200
    And I get a response body with at least 2 employee objects

  Scenario: Get all employees with an invalid token
    Given I have an invalid authentication token
    When I call the get all employees endpoint
    Then The status code is 403
