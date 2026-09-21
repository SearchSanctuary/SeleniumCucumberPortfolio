package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.CheckoutPage;
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
        assertTrue(cartPage.isDisplayed());
    }

    @Then("I should see the {string} in the cart")
    public void iShouldSeeTheProductInTheCart(String productName) {
        assertTrue(cartPage.containsProduct(productName));
    }

    @When("I remove the {string} from the cart")
    public void iRemoveTheProductFromCart(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("I should not see the {string} in the cart")
    public void iShouldNotSeeTheProductInTheCart(String productName) {
        assertTrue(cartPage.doesNotContainProduct(productName));
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        cartPage.clickCheckoutButton();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        assertTrue(checkoutPage.isCheckoutTitleVisible());
    }
}
