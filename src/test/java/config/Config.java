package config;

public class Config {

    public static final String BASE_URL =
            System.getProperty("baseUrl", "https://saucedemo.com/");

    public static final boolean HEADLESS =
            Boolean.parseBoolean(System.getProperty("headless", "false"));
}