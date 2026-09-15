package hooks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static WebDriver driver;

    public static void startDriver() {
        if (driver == null)
            driver = new ChromeDriver();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}
