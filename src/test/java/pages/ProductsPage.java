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

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productTitle = By.className("title");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productTitle)
        ).isDisplayed();
    }

    public void addProductToCart(String productName) {
        By addToCartButton = By.xpath(
                "//div[@data-test='inventory-item-name' " +
                        "and text()='"
                + productName
                +"']/ancestor::div[@data-test='inventory-item-description']//button"
        );

        WebElement addButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(addToCartButton)
        );

        // Native Selenium clicks were unreliable for this application,
        // so JavaScript click is used after waiting for the button to be clickable.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                addButton
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                addButton
        );

        // Verify the item has been added
        By removeFromCartButton = By.id(
                "remove-" + productName.toLowerCase().replace(" ", "-"));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(removeFromCartButton)
        );
    }

    public void openShoppingCart() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartLink)
        ).click();
    }

    public int getCartItemCount() {
        return Integer.parseInt(
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(cartBadge)
                ).getText()
        );
    }

}
