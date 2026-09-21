Feature: Checkout

  @smoke
  @regression
  @checkout
  Scenario: Complete Checkout Successfully
    Given I am on the login page
    When I login with valid credentials
    And I add the "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    And I proceed to checkout
    Then I should see the checkout page
    When I enter the checkout information "John" "Doe" and "E15 3BA"
    And I continue to the order overview
    Then I should see the order overview
    And The order should contain "Sauce Labs Backpack"
    And The order total should be displayed
    When I finish the order
    Then I should see the order confirmation
    And I should see the order confirmation message
