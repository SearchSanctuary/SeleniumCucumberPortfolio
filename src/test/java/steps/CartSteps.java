package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private ProductsPage productsPage;
    private CartPage cartPage;

    @When("I open the shopping cart")
    public void iOpenTheShoppingCart() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.openShoppingCart();

        cartPage = new CartPage(DriverManager.getDriver());
    }

    @Then("I should see the {string} in the cart")
    public void iShouldSeeTheProductInTheCart(String productName) {
        assertTrue(cartPage.containsProduct(productName));
    }
}
