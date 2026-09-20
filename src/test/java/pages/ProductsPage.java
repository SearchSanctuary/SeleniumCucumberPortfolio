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

    public ProductsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS));
    }

    public boolean isDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productTitle)
        ).isDisplayed();
    }
}
