package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.LoginPage;
import pages.ProductsPage;

public class Hooks {
    @Before
    public void setUp() {
        DriverManager.startDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()){
            byte[] screenshot = (
                    (TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png","failure screenshot");
        }
        DriverManager.quitDriver();
    }
}
