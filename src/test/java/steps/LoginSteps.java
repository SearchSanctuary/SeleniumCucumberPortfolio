package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        System.out.println("I am on the login page");
    }

    @When("I login with valid credentials")
    public void iLogInWithValidCredentials() {
        System.out.println("I log in with valid credentials");
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        System.out.println("I should see the products page");
    }

}
