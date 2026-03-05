package askomdch.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    // ThreadLocal ensures parallel tests don't share the same browser session
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            ChromeOptions options = new ChromeOptions();

            // Reads the pipeline parameter: mvn clean verify -Dheadless=true
            String isHeadless = System.getProperty("headless");
            if (isHeadless != null && isHeadless.equalsIgnoreCase("true")) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-dev-shm-usage"); // ADD THIS: Prevents memory crash
                options.addArguments("--no-sandbox");            // ADD THIS: Required for CI environments
            }

            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // You MUST remove the thread to prevent memory leaks
        }
    }
}