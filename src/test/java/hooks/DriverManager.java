package hooks;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void startDriver() {
        if (driver.get() == null){
            ChromeOptions options = new ChromeOptions();

            if (Config.HEADLESS){
                options.addArguments("--headless=new");
            }

            driver.set(new ChromeDriver(options));
        }

    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null){
            driver.get().quit();
            driver.remove();
        }
    }
}
