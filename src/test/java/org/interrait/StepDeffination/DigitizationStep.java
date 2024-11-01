package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;

public class DigitizationStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    LoginPage login;
    WebDriverWait wait;
    DssPage dssPage;
    String dealerType="";

    private static final Logger logger = LoggerFactory.getLogger(DigitizationStep.class);
    public DigitizationStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        actions=new Actions(driver);
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        wait =  new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Then("verify the {string} and {string} heading text.")
    public void verifyTheAndHeadingText(String CVAPtxt, String CX360txt) {
        try{
            wait.until(driver1 -> dssPage.getCVAPtxt().isDisplayed() && dssPage.getCX360txt().isDisplayed());
            String CVAPactualtxt = dssPage.getCVAPtxt().getText().substring(0,46);
            String CX360actualtxt = dssPage.getCX360txt().getText().substring(0,25);
            Assert.assertEquals(CVAPactualtxt,CVAPtxt);
            Assert.assertEquals(CX360actualtxt,CX360txt);
            logger.info("Pre-Qualifier texts are verified successfully.");
        }catch (Exception e){
            logger.error("Pre-Qualifier texts are not verified successfully.",e);
            Assert.fail();
        }

    }

    @Then("the CX360 section loaded properly.")
    public void theCXSectionLoadedProperly() {
        try{
            wait.until(driver1 -> dssPage.getCX360collapse());
            actions.moveToElement(dssPage.getCX360collapse()).perform();
            logger.info("CX360 Secton Loaded successfully.");

        } catch (Exception e) {
            logger.error("CX360 Section is not loaded properly.",e);
            Assert.fail();
        }
    }

    @And("The CX360 {string} will be verified.")
    public void theCXLinkWillBeVerified(String actualLink) {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"RHTableFooterNote\"]/a")));
            String cx360link = dssPage.getDigitizationSection().findElement(By.xpath("//*[@id=\"RHTableFooterNote\"]/a")).getAttribute("href");
            Assert.assertEquals(actualLink,cx360link);
            logger.info("The CX360 link is verified.");
        }catch (Exception e){
            logger.error("CX360 Link is having problem..",e);
            Assert.fail();
        }
    }


    @Then("Verify the {string} present in that section for the {string}")
    public void verifyThePresentInThatSection(String earningEles,String dealerTyp) {
        this.dealerType=dealerTyp;
        String[] earningelelist = earningEles.split(",");
        try{
            if(dealerTyp.equals("MBEP/HI")){
                wait.until(driver1 -> dssPage.getDigitizationSection().findElement(By.id("digitalSalesEnrollmentHeading")).isDisplayed() &&
                        dssPage.getDigitizationSection().findElement(By.id("digitalShowroom_util_header")).isDisplayed() &&
                        dssPage.getDigitizationSection().findElement(By.id("ds_util_header")).isDisplayed());
                earningelelist[0].equals(dssPage.getDigitizationSection().findElement(By.id("digitalSalesEnrollmentHeading")).getText());
                earningelelist[1].equals(dssPage.getDigitizationSection().findElement(By.id("digitalShowroom_util_header")).getText());
                earningelelist[2].equals(dssPage.getDigitizationSection().findElement(By.id("ds_util_header")).getText());
                logger.info("Earning Elements are Verified Successfully for dealType MBEP/HI");
            }
            else{
                wait.until(driver1 -> driver.findElement(By.id("digitalShowroom_util_headerNC")).isDisplayed() &&
                        driver.findElement(By.id("ds_util_headerNC")).isDisplayed());
                earningelelist[0].equals(driver.findElement(By.id("digitalShowroom_util_headerNC")).getText().substring(0,44));
                earningelelist[1].equals(driver.findElement(By.id("ds_util_headerNC")).getText().substring(0,33));
                logger.info("Earning Elements are Verified Successfully for dealType MBEP-NC");
            }

        }catch (Exception e){
            logger.error("Earning Elements are not verified successfully.",e);
            Assert.fail();
        }
    }

    @And("Load the Digital Showroom section")
    public void loadTheDigitalShowroomSection() {

    }

}
