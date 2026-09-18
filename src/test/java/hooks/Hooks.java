package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import pages.LoginPage;
import pages.ProductsPage;

public class Hooks {
    @Before
    public void setUp() {
        DriverManager.startDriver();
        System.out.println("Starting scenario on thread: " + Thread.currentThread().getName());
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
