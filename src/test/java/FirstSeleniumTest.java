import hooks.DriverManager;
import org.junit.jupiter.api.Test;


public class FirstSeleniumTest {
    @Test
    void shouldOpenSauceDemo() {
        DriverManager.startDriver();

        DriverManager.getDriver().get("https://saucedemo.com");

        DriverManager.quitDriver();
    }
}
