package org.interrait.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestBase {
    // initiate the drive and return when drivermanager will be called.
    WebDriver driver;
    String url;


    public WebDriver webDriverManager() throws IOException {
        File file = new File("src/test/resources/global.properties");
        FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
        Properties prop = new Properties();
        prop.load(fis);
        url = prop.getProperty("QAUrl");
        String browser = prop.getProperty("browser");
//  String browser = browser_maven!=null ? browser_maven : browser_properties;
        if(driver == null)
        {
            if(browser.equalsIgnoreCase("chrome"))
            {
                driver = new ChromeDriver();// driver gets the life
            }
            if(browser.equalsIgnoreCase("firefox"))
            {
                driver = new FirefoxDriver();
            }
            assert driver != null;
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//            System.out.println(url);
            driver.get(url);
        }
        return driver;
    }

}
