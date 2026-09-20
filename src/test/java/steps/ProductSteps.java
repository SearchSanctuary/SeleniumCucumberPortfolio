package steps;

import hooks.DriverManager;
import io.cucumber.java.en.When;
import pages.ProductsPage;

public class ProductSteps {
    private ProductsPage productsPage;

    @When("I add the {string} to the cart")
    public void iAddTheProductToTheCart(String productName) {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.addProductToCart(productName);
    }
}
