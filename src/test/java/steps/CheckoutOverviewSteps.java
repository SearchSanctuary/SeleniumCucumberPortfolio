package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CheckoutOverviewPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutOverviewSteps {

    private CheckoutOverviewPage overviewPage;

    @Then("I should see the order overview")
    public void iShouldSeeTheOrderOverview() {
        overviewPage = new CheckoutOverviewPage(DriverManager.getDriver());
        assertTrue(overviewPage.isOverviewTitleVisible());
    }

    @Then("The order should contain {string}")
    public void orderShouldContainProduct(String productName) {
        if (overviewPage == null) { overviewPage = new CheckoutOverviewPage(DriverManager.getDriver()); }

        assertTrue(overviewPage.containsProduct(productName));
    }

    @Then("The order total should be displayed")
    public void orderTotalShouldBeDisplayed() {
        assertTrue(overviewPage.isTotalDisplayed());
    }

    @When("I finish the order")
    public void iFinishTheOrder() {
        overviewPage.clickFinish();
    }
}
