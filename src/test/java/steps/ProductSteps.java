package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSteps {
    private ProductsPage productsPage;

    @When("I add the {string} to the cart")
    public void iAddTheProductToTheCart(String productName) {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.addProductToCart(productName);
    }

    @Then("The cart should contain {int} item")
    public void theCartShouldContainItem(int expectedCount) {
        assertEquals(expectedCount, productsPage.getCartItemCount());
    }
}
