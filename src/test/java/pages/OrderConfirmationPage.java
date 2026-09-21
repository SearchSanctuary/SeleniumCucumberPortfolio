package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderConfirmationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By confirmationTitle = By.className("title");
    private final By confirmationHeader = By.cssSelector("[data-test='complete-header']");
    private final By backToHomeButton = By.id("back-to-products");

    public OrderConfirmationPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public boolean isConfirmationTitleVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmationTitle)
        ).getText().equals("Checkout: Complete!");
    }

    public String getConfirmationHeader() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmationHeader)
        ).getText();
    }

    public void clickBackHome() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(backToHomeButton)
        ).click();
    }
}
