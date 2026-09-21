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

  @negative
  @regression
  @checkout
  Scenario: Checkout with missing customer information
    Given I am on the login page
    When I login with valid credentials
    And I add the "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    And I proceed to checkout
    Then I should see the checkout page
    When I continue to the order overview
    Then I should see the checkout error "Error: First Name is required"

  @regression
  @checkout
  Scenario: Cancel checkout and return to the cart
    Given I am on the login page
    When I login with valid credentials
    And I add the "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    And I proceed to checkout
    Then I should see the checkout page
    When I click the back button
    Then I should see the cart page
    And I should see the "Sauce Labs Backpack" in the cart

  @regression
  @checkout
  Scenario: Complete checkout with multiple products
    Given I am on the login page
    When I login with valid credentials
    And I add the "Sauce Labs Backpack" to the cart
    And I add the "Sauce Labs Bike Light" to the cart
    And The cart should contain 2 item
    And I open the shopping cart
    Then I should see the "Sauce Labs Backpack" in the cart
    And I should see the "Sauce Labs Bike Light" in the cart
    When I proceed to checkout
    And I enter the checkout information "Aaron" "Test" and "SE10 0AA"
    And I continue to the order overview
    Then I should see the order overview
    And The order should contain "Sauce Labs Backpack"
    And The order should contain "Sauce Labs Bike Light"
    And The order total should be displayed
    When I finish the order
    Then I should see the order confirmation