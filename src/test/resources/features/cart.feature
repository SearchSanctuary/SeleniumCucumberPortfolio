Feature: Cart

  @smoke
  @regression
  @cart
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

  @cart
  Scenario Outline: Remove item from cart
    Given I am on the login page
    When I login with valid credentials
    And I add the "<product>" to the cart
    And I open the shopping cart
    And I remove the "<product>" from the cart
    Then I should not see the "<product>" in the cart

    Examples:
      | product |
      | Sauce Labs Backpack |
      | Sauce Labs Bike Light |
      | Sauce Labs Bolt T-Shirt |
