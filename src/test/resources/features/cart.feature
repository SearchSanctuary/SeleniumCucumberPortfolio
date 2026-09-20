Feature: Cart

  @smoke
  @regression
  Scenario: Add product to shopping cart
    Given I am on the login page
    When I login with valid credentials
    And I add the backpack to the cart
    And I open the shopping cart
    Then I should see the backpack in the cart