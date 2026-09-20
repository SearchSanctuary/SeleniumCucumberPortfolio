package config;

public class Config {

    public static final String BASE_URL =
            System.getProperty("baseUrl", "https://saucedemo.com/");

    public static final boolean HEADLESS =
            Boolean.parseBoolean(System.getProperty("headless", "false"));

    public static final int EXPLICIT_WAIT_SECONDS =
            Integer.parseInt(System.getProperty("explicitWait", "10"));

    public static final String BROWSER =
            System.getProperty("browser", "chrome");
}