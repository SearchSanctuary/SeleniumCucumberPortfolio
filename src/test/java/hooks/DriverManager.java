package hooks;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void startDriver() {
        if (driver.get() == null){

            /* Start browser based on given browser type */
            if (Config.BROWSER.equalsIgnoreCase("chrome")){
                /* Set options for driver */
                ChromeOptions options = new ChromeOptions();
                if (Config.HEADLESS){
                    options.addArguments("--headless=new");
                }
                options.addArguments("--disable-features=PasswordLeakDetection");
                options.setExperimentalOption(
                        "prefs",
                        java.util.Map.of(
                                "profile.password_manager_leak_detection",
                                false
                        )
                );
                driver.set(new ChromeDriver(options));

            } else if (Config.BROWSER.equalsIgnoreCase("safari")){
                driver.set(new SafariDriver());

            } else {
                throw new IllegalArgumentException(
                        "Unsupported browser: " + Config.BROWSER
                );
            }

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
