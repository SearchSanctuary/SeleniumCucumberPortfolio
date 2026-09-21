package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CheckoutPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    private CheckoutPage checkoutPage;

    @Then("I should see the checkout page")
    public void iShouldSeeTheCheckoutPage() {
        checkoutPage = new CheckoutPage(DriverManager.getDriver());
        assertTrue(checkoutPage.isCheckoutTitleVisible());
    }

    @When("I enter the checkout information {string} {string} and {string}")
    public void iEnterTheCheckoutInformation(String firstName, String lastName, String postCode) {
        if (checkoutPage == null) { checkoutPage = new CheckoutPage(DriverManager.getDriver()); }
        assertTrue(checkoutPage.isCheckoutTitleVisible());

        checkoutPage.enterCustomerInformation(firstName, lastName, postCode);
    }

    @When("I continue to the order overview")
    public void continueToOrderOverview() {
        checkoutPage.clickContinueButton();
    }


}
