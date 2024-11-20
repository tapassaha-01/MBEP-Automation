package org.interrait.StepDeffination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.LandingPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;

public class CommonStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    LoginPage login;
    WebDriverWait wait;
    DssPage dssPage;

    private static final Logger logger = LoggerFactory.getLogger(CommonStep.class);
    public CommonStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
         driver = this.contextSetup.getTestBase().webDriverManager();

        actions=new Actions(driver);
    }
    @Given("User is on WSL Login DSS")
    public void user_is_on_wsl_login_dss() {
        login = this.contextSetup.getPageObjectManager().getLoginPage();
        driver.manage().window().maximize();
    }
    @When("User login into application with {string} and {string}")
    public void user_login_into_application_with_and(String username, String pass) throws InterruptedException {
        login.loginApplicaiton(username, pass);
        Thread.sleep(2000);
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        try {
            // Check if the alert is present
            if (dssPage.isAlertPresent(driver)) {
                Alert alert = driver.switchTo().alert();
                logger.info("Alert text: {}", alert.getText());
                // Accept the alert
                alert.accept();
            }
        } catch (NoAlertPresentException e) {
            logger.info("No alert present.");
        }

        try {
            Thread.sleep(2000);
            // Check if the pop-up button is displayed
            if (dssPage.getPOP_UP_3_btn().isDisplayed()) {
                logger.info("Pop-up button is displayed. Clicking the button.");
                dssPage.getPOP_UP_3_btn().click();
            }
        } catch (NoSuchElementException e) {
            logger.info("Login has some issue..{}",e.getMessage());
        }
    }


    @Then("select dlrcd as {string}, mon {string}, year {string} and click on submit")
    public void selectDlrcdAsMonYearAndClickOnSubmit(String dlrcd, String mon, String year) throws InterruptedException {
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        dssPage.searchDealer(dlrcd,mon,year);
        dssPage.submitsearchbtn.click();
        Thread.sleep(2000);
    }


}
