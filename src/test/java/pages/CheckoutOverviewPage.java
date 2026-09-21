package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutOverviewPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By overviewTitle = By.className("title");
    private final By finishButton = By.id("finish");
    private final By cartItems = By.cssSelector(
            "[data-test='cart-list'] [data-test='inventory-item-name']");
    private final By total = By.cssSelector("[data-test='total-label']");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public boolean isOverviewTitleVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(overviewTitle)
        ).getText().equals("Checkout: Overview");
    }

    public boolean containsProduct(String productName) {
        return wait.until(
                driver -> driver
                        .findElements(cartItems)
                        .stream()
                        .anyMatch(product ->
                                product.getText()
                                        .equals(productName))
        );
    }

    public boolean isTotalDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(total)
        ).isDisplayed();
    }

    public void clickFinish() {
        wait.until(
                ExpectedConditions.elementToBeClickable(finishButton)
        ).click();
    }
}
