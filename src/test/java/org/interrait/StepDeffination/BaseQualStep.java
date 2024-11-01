package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BaseQualStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    DssPage dssPage;
    WebDriverWait wait ;
    LoginPage login;
    int isSkip=0;
    List<String> monStatus=new ArrayList<String>();
    private static final Logger logger = LoggerFactory.getLogger(BaseQualStep.class);
    public BaseQualStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        actions=new Actions(driver);
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        wait =  new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Then("check the {string} and {string}")
    public void checkTheAnd(String Heading, String Description) {
        try {
            wait.until(driver ->
                    dssPage.getBaseQualHeading().isDisplayed() && dssPage.getBaseQualDescription().isDisplayed()
            );
            Assert.assertEquals(Heading,dssPage.getBaseQualHeading().getText());
            Assert.assertEquals(Description,dssPage.getBaseQualDescription().getText());
            logger.info("Base QualifierHeading and Description is verified.");
        }catch (Exception e) {
            logger.error("Base QualifierHeading and Description is not verified.", e);
            Assert.fail();
        }
    }

    @And("Collapse functionality is working properly.")
    public void collapseFunctionalityIsWorkingProperly() {
        try{
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(dssPage.getBaseQualCollapse()));
            actions.moveToElement(dssPage.getBaseQualCollapse()).perform();
            actions.moveToElement(dssPage.getBaseQualCollapse()).perform();
            logger.info("Collapse functionality of Base Qualifier is working.");

        }catch (Exception e){
            Assert.fail();
            logger.error("Collapse functionality for Base Qualifier has some problem.",e);
        }
    }

    @Then("Open the FSL section")
    public void openTheFSLSection() {
        try{
            wait.until(ExpectedConditions.visibilityOf(dssPage.getFSLcollapse()));
            actions.moveToElement(dssPage.getFSLcollapse()).perform();
            logger.info("FSL Section of Base Qualifier is loaded.");

        }catch (Exception e){
            logger.error("FSL section of Base Qualifier is not loading...",e);
            Assert.fail();

        }
    }

    @And("Verify the {string} and {string}")
    public void verifyTheAnd(String heading, String description) {
            try{
                wait.until(driver1 -> dssPage.getFSLHeading().isDisplayed() && dssPage.getFSLDescription().isDisplayed());
                Assert.assertEquals(heading,dssPage.getFSLHeading().getText().substring(0,14));
                Assert.assertEquals(description,dssPage.getFSLDescription().getText());
                logger.info("FSL heading and description are verified.");
            }catch (Exception e){
                logger.error("FSL Section heading and description is failed to validate.",e);
                Assert.fail();
            }
    }

    @Then("Verify the DataAgreement as {string} and {string}")
    public void verifyTheDataAgreementAsAnd(String heading, String description) {
        try{
            wait.until(driver1 -> dssPage.getDataAgreeHeading().isDisplayed() && dssPage.getDtaAgreementDescription().isDisplayed());
            Assert.assertEquals(heading,dssPage.getDataAgreeHeading().getText().substring(0,20));
            Assert.assertEquals(description,dssPage.getDtaAgreementDescription().getText());
            logger.info("Data Agreement heading and description are verified.");
        }catch (Exception e){
            logger.error("Data Agreement heading and description is failed to validate.",e);
            Assert.fail();
        }
    }

    @And("Verify the {string} present in the FSL")
    public void verifyThePresentInTheFSL(String fsleles) {
        String[] fslelellist = fsleles.split(",");
        try{
//            wait.until(driver1 -> dssPage.getFSLtable().isDisplayed());
            Thread.sleep(2000);
            List<WebElement> fslelelist = dssPage.getFSLtable().findElements(By.xpath("//*[@id=\"facilityDtlTbl\"]/tr"));
            for(int i=2;i< fslelelist.size();i++){
                Assert.assertEquals(fslelellist[i-2],fslelelist.get(i).findElements(By.tagName("td")).get(1).getText());
            }
            logger.info("FSl Elements are verified successfully.");
        }catch (Exception e){
            logger.error("FSl Elements verification is faild.",e);
            Assert.fail();
        }
    }

    @Then("Verify that MBEP_2016_Q3_to_2018_Q2 Page is opening correctly.")
    public void verifyThatMBEPQToQPageIsOpeningCorrectly() {
        try{
            wait.until(driver1 ->dssPage.getPerformance_payment_menu().isDisplayed());
            actions.moveToElement(dssPage.getPerformance_payment_menu()).perform();
            dssPage.getMBEP_2016().click();
            wait.until(driver1 -> dssPage.getMBEP_2016_heading());
            Assert.assertEquals("MBEP 2018 Dealer Status Summary",dssPage.getMBEP_2016_heading().getText().trim());
            logger.info("MBEP_2016_Q3_to_2018_Q2 is coming correctly.");
            actions.moveToElement(dssPage.getPerformance_payment_menu()).perform();
            dssPage.getMBEP_2023().click();
        }catch (Exception e){
            logger.error("Having problem with the MBEP (2016 Q3 to 2018 Q2)", e);
            Assert.fail();
        }
    }


    @When("Base-Qualifer is not achieved.")
    public void baseQualiferIsNotAchieved() {
        try{
            wait.until(driver1 ->  dssPage.getBaseQualifier().findElement(By.id("baseQualifierPrvMthSts")).isDisplayed() &&
            dssPage.getBaseQualifier().findElement(By.id("baseQualifierCurrMthSts")).isDisplayed());
            String earningMon = dssPage.getBaseQualifier().findElement(By.id("baseQualifierPrvMthSts")).getText();
            String trackingMon = dssPage.getBaseQualifier().findElement(By.id("baseQualifierCurrMthSts")).getText();
            if(earningMon.equals("NOT ACHIEVED")){
                monStatus.add("earningMon");
                monStatus.add(earningMon);
                isSkip+=1;
            }
            if(trackingMon.equals("NOT ACHIEVED")){
                monStatus.add("trackingMon");
                monStatus.add(trackingMon);
                isSkip+=1;
            }
            logger.info("Earning month Base qualifier is {}", earningMon);
            logger.info("Tracking month Base qualifier is {}",trackingMon);
        }catch (Exception e){
            logger.error("Exception occurred while checking base-qualifier NOT ACHIEVED status");
            Assert.fail();
        }

    }

    @Then("Brand-Section will show {string}.")
    public void brandSectionWillShow(String status) {
        if(isSkip<1){
            logger.debug("skipped Band-Section Checking..");
            return;
        }
        try{
            wait.until(driver1 -> dssPage.getBrandSection().findElement(By.id("brandCommitmentPrvMnthSts")).isDisplayed()
                        && dssPage.getBrandSection().findElement(By.id("brandCommitmentCrntMnthSts")).isDisplayed());
            if(monStatus.stream().anyMatch(e->e.equals("earningMon"))){
                Assert.assertEquals(dssPage.getBrandSection().findElement(By.id("brandCommitmentPrvMnthSts")).getText(),status);
                logger.info("Brand-Section earning month status is verified");
            }
            if(monStatus.stream().anyMatch(e->e.equals("trackingMon"))){
                Assert.assertEquals(dssPage.getBrandSection().findElement(By.id("brandCommitmentCrntMnthSts")).getText(),status);
                logger.info("Brand-Section tracking month status is verified");
            }
        }catch (Exception e){
            logger.error("Brand-Section NOT ACHIEVED checking has some error..",e);
            Assert.fail();
        }
    }

    @And("L&RSection will show {string}")
    public void lRSectionWillShow(String status) {
        if(isSkip<1){
            logger.debug("skipped L&R-Section Checking..");
            return;
        }
        try{
            Thread.sleep(2000);
            wait.until(driver1 -> driver.findElement(By.id("loyaltyAndRetentionPrvMthSts")).isDisplayed()
                    && driver.findElement(By.id("loyaltyAndRetentionCrrMthSts")).isDisplayed());
            if(monStatus.stream().anyMatch(e->e.equals("earningMon"))){
                Assert.assertEquals(driver.findElement(By.id("loyaltyAndRetentionPrvMthSts")).getText(),status);
                logger.info("L&R-Section earning month status is verified");
            }
            if(monStatus.stream().anyMatch(e->e.equals("trackingMon"))){
                Assert.assertEquals(driver.findElement(By.id("loyaltyAndRetentionCrrMthSts")).getText(),status);
                logger.info("L&R-Section tracking month status is verified");
            }
        }catch (Exception e){
            logger.error("L&R-Section NOT ACHIEVED checking has some error..",e);
            Assert.fail();
        }
    }

    @And("Digitization will show {string}")
    public void digitizationWillShow(String status) {
        if(isSkip<1){
            logger.debug("skipped Digitization-Section Checking..");
            return;
        }
        try{
            wait.until(driver1 -> driver.findElement(By.id("dmPrvMthSts")).isDisplayed()
                    && driver.findElement(By.id("dmCrrMthSts")).isDisplayed());
            if(monStatus.stream().anyMatch(e->e.equals("earningMon"))){
                Assert.assertEquals(driver.findElement(By.id("dmPrvMthSts")).getText(),status);
                logger.info("Digitization-Section earning month status is verified");
            }
            if(monStatus.stream().anyMatch(e->e.equals("trackingMon"))){
                Assert.assertEquals(driver.findElement(By.id("dmCrrMthSts")).getText(),status);
                logger.info("Digitization-Section tracking month status is verified");
            }
        }catch (Exception e){
            logger.error("Digitization-Section NOT ACHIEVED checking has some error..",e);
            Assert.fail();
        }
    }
}
