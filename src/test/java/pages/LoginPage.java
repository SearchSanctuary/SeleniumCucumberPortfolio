package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By loginErrorMessage = By.className("error-message-container");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public void open() {
        driver.get(Config.BASE_URL);
    }

    public void enterUsername(String username) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameField))
                .sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField))
                .sendKeys(password);
    }

    public void clickLogin(){
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    public boolean isLoginErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginErrorMessage)
        ).isDisplayed();
    }

    public String getLoginErrorMessage(){
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginErrorMessage)
        ).getText();
    }
}
