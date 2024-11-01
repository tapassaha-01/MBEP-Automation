package org.interrait.StepDeffination;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;


public class Hooks{

    public testContextSetup contextSetup;
    public WebDriver driver;
    public Hooks(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
//
    }
    // This method will run after each scenario
    @After
    public void tearDown() {
        System.out.println("Closing the browser...");
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterStep
    public void addScreenShots(Scenario scenario) throws IOException {
        if (scenario.isFailed()){
            File sourcePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            byte[] filecontent = FileUtils.readFileToByteArray(sourcePath);
            scenario.attach(filecontent,"image/png","image");
            driver.quit();
        }
    }
}