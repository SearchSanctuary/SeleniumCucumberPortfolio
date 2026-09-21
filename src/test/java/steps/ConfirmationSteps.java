package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.OrderConfirmationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfirmationSteps {

    OrderConfirmationPage confirmationPage;

    @Then("I should see the order confirmation")
    public void iShouldSeeTheOrderConfirmation() {
        confirmationPage = new OrderConfirmationPage(DriverManager.getDriver());

        assertTrue(confirmationPage.isConfirmationTitleVisible());
    }

    @Then("I should see the order confirmation message")
    public void iShouldSeeOrderConfirmationMessage() {
        assertEquals("Thank you for your order!", confirmationPage.getConfirmationHeader());
    }

    @When("I return to the home page")
    public void iReturnToTheProductsPage() {
        confirmationPage.clickBackHome();
    }
}
