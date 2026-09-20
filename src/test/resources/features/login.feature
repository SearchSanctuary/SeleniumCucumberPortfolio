Feature: Login
  @smoke
  Scenario: Successful Login
    Given I am on the login page
    When I login with valid credentials
    Then I should see the products page

  @regression
  @negative
  Scenario: Invalid Login
    Given I am on the login page
    When I login with invalid credentials
    Then I should see a login error

