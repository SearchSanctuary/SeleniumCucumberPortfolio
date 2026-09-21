package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By checkoutTitle = By.className("title");
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public boolean isCheckoutTitleVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(checkoutTitle)
        ).getText().equals("Checkout: Your Information");
    }

    public void inputFirstName(String firstName) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstNameInput)
        ).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void inputLastName(String lastName) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastNameInput)
        ).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void inputPostalCode(String postalCode) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(postalCodeInput)
        ).clear();
        driver.findElement(postalCodeInput).sendKeys(postalCode);
    }

    public void enterCustomerInformation(String firstName, String lastName, String postCode) {
        inputFirstName(firstName);
        inputLastName(lastName);
        inputPostalCode(postCode);
    }

    public void clickBackButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(cancelButton)
        ).click();
    }

    public void clickContinueButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }


}
