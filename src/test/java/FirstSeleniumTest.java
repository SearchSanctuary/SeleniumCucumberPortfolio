import hooks.DriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstSeleniumTest {
    @Test
    void shouldOpenSauceDemo() {
        DriverManager.startDriver();

        DriverManager.getDriver().get("https://saucedemo.com");

        DriverManager.quitDriver();
    }
}
