package steps;

import hooks.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import pages.ProductsPage;
import testdata.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage = new LoginPage(DriverManager.getDriver());
        productsPage = new ProductsPage(DriverManager.getDriver());

        loginPage.open();
    }

    @When("I login with valid credentials")
    public void iLogInWithValidCredentials() {
        loginPage.enterUsername(TestData.VALID_USERNAME);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.clickLogin();
    }

    @When("I login with invalid credentials")
    public void iLogInWithInvalidCredentials() {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("invalid_password");
        loginPage.clickLogin();
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        assertTrue(productsPage.isDisplayed());
    }

    @Then("I should see a login error")
    public void iShouldSeeALoginError() {
        assertTrue(loginPage.isLoginErrorDisplayed());

        assertEquals(
                "Epic sadface: Username and password do not match any user in this service",
                loginPage.getLoginErrorMessage()
        );
    }

}
