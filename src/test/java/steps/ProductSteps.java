package steps;

import hooks.DriverManager;
import io.cucumber.java.en.When;
import pages.ProductsPage;

public class ProductSteps {
    private ProductsPage productsPage;

    @When("I add the backpack to the cart")
    public void iAddTheBackpackToTheCart() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.addProductToCart();
    }
}
