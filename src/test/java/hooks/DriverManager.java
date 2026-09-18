package hooks;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static WebDriver driver;

    public static void startDriver() {
        if (driver == null){
            ChromeOptions options = new ChromeOptions();

            if (Config.HEADLESS){
                options.addArguments("--headless=new");
            }

            driver = new ChromeDriver(options);
        }

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
