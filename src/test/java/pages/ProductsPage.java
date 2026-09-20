package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productTitle = By.className("title");
    private final By backpackAddToCart = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartLink = By.className("shopping_cart_link");

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

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(addToCartButton)
        ).click();
    }

    public void openShoppingCart() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartLink)
        ).click();
    }
}
