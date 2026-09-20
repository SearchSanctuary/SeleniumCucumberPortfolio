Feature: Cart

  @smoke
  @regression
  Scenario Outline: Add product to shopping cart
    Given I am on the login page
    When I login with valid credentials
    And I add the "<product>" to the cart
    And The cart should contain 1 item
    And I open the shopping cart
    Then I should see the "<product>" in the cart

    Examples:
    | product |
    | Sauce Labs Backpack |
    | Sauce Labs Bike Light |
    | Sauce Labs Bolt T-Shirt |