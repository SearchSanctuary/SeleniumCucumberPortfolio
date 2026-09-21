package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartTitle = By.className("title");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartTitle)
        ).isDisplayed();
    }

    public boolean containsProduct(String productName) {
        By cartProducts = By.cssSelector(
                "[data-test='cart-list'] [data-test='inventory-item-name']"
        );

        return wait.until(driver ->
                driver.findElements(cartProducts)
                        .stream()
                        .anyMatch(product ->
                                product.getText().equals(productName)
                        )
        );
    }


    public void removeProduct(String productName) {
        String productId = productName
                .toLowerCase()
                .replace(" ", "-");

        By removeButton = By.id("remove-" + productId);

        WebElement removeButtonElement = wait.until(
                ExpectedConditions.elementToBeClickable(removeButton)
        );

        // Native Selenium clicks were unreliable for this application,
        // so JavaScript click is used after waiting for the button to be clickable.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                removeButtonElement
        );
    }

    public boolean doesNotContainProduct(String productName) {
        By cartProducts = By.cssSelector(
                "[data-test='cart-list'] [data-test='inventory-item-name']"
        );

        return wait.until(driver ->
                driver.findElements(cartProducts)
                        .stream()
                        .noneMatch(product ->
                                product.getText().equals(productName)
                        )
        );
    }
}
