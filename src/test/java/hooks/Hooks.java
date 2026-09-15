package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    @Before
    public void setUp() {
        DriverManager.startDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
