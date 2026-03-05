package askomdch.hooks;

import askomdch.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class Hooks {
    @After
    public void tearDown(Scenario scenario) {
        // Safe screenshot logic
        if (scenario.isFailed() && DriverFactory.getDriver() != null) {
            byte[] screenshotByte = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failed Step Screenshot", new ByteArrayInputStream(screenshotByte));
        }

        // This will now actually execute because we removed the broken file path logic
        DriverFactory.quitDriver();
    }
}