package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.EarningDetails;
import org.interrait.PageObjects.EarningSummeryPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EarningSummeryStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;

    WebDriverWait wait;
    DssPage dssPage;

    JavascriptExecutor js;
    EarningDetails earningDetails;
    EarningSummeryPage earningSummeryPage;
    Map<String, Integer> monthLst;
    Select select;
    String mon;


    private static final Logger logger = LoggerFactory.getLogger(EarningSummeryStep.class);

    public EarningSummeryStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        actions = new Actions(driver);
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        earningSummeryPage = this.contextSetup.getPageObjectManager().getEarningSummeryPage();
        earningDetails =  this.contextSetup.getPageObjectManager().getEarningDetails();
        monthLst = earningDetails.monthLst;
        js = (JavascriptExecutor) driver;
    }

    @Then("Verifying the Earning Summery page is loading properly")
    public void verifyingTheEarningSummeryPageIsLoadingProperly() {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a")).isDisplayed());
            actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[3]/a")).click();
            actions.moveToElement(this.earningSummeryPage.getEarningSummeryMenu()).click();
            Thread.sleep(5000);
            wait.until(driver1 -> this.earningSummeryPage.getEarningSummeryHeading().isDisplayed());
            Assert.assertEquals(this.earningSummeryPage.getEarningSummeryHeading().getText(),"Earnings Summary");
            logger.debug("The Heading of the page is {}",this.earningSummeryPage.getEarningSummeryHeading().getText());
        }catch (Exception e){
            logger.error("Earning Element has some error..",e);
            Assert.fail();
        }
    }

    @And("Verify there have {int} columns in the table.")
    public void verifyThereHaveColumnsInTheTable(int columnCount) {
        try{
            wait.until(driver1 -> this.earningSummeryPage.getSummeryTableHeader().get(2).isDisplayed());
            logger.info("The column count of Summery Page table headers.. {}",this.earningSummeryPage.getSummeryTableHeader().size()-1);
            Assert.assertEquals(this.earningSummeryPage.getSummeryTableHeader().size()-1,columnCount);
        }
        catch (Exception e){
            logger.error("Having some error in getting the table header.. {} ",e.getMessage());
            Assert.fail();
        }
    }

    @Then("enter dlrcd as {string}, mon {string}, year {string} and click on submit")
    public void enterDlrcdAsMonYearAndClickOnSubmit(String dlrcd, String mon, String year) {
        this.mon =mon;
        try{
            wait.until(driver1 -> this.earningSummeryPage.getDealerCode().isDisplayed()&&
                    this.earningSummeryPage.getMonDropDown().isDisplayed()&&
                    this.earningSummeryPage.getYearDropDown().isDisplayed());
            this.earningSummeryPage.getDealerCode().sendKeys(dlrcd);
            select = new Select(this.earningSummeryPage.getMonDropDown());
            select.selectByValue(mon);
            select = new Select(this.earningSummeryPage.getYearDropDown());
            select.selectByValue(year);
            this.earningSummeryPage.getSubmitBtn().click();
            Thread.sleep(2000);
        }catch (Exception e){
            logger.error("Having some error in selecting dealer,mon,year {}",e.getMessage());
            Assert.fail();
        }
    }

    @And("Verify that Forfeited_Escrow_$ row added below the Brand Commitment_\\(payment + Escrow)")
    public void verifyThatForfeited_Escrow_$RowAddedBelowTheBrandCommitmentPaymentEscrow() {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody")).isDisplayed());
            Thread.sleep(2000);
            String firstEle = driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody/tr[2]/td[1]")).getText();//*[@id="area_code"]/tbody/tr[2]/td[1]
            String secondEle = driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody/tr[3]/td[1]")).getText();
            logger.info("First element in the Brand Commitment Section {} and the second element is {} present.",firstEle,secondEle);
        }catch (Exception e){
            logger.error("There have some error while getting the elements.. {}",e.getMessage());
            Assert.fail();
        }
    }


    @When("Click on the Earning_details")
    public void clickOnTheEarning_details() {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"area_code_6\"]/tbody/tr/td[14]/a")).isDisplayed());
            driver.findElement(By.xpath("//*[@id=\"area_code_6\"]/tbody/tr/td[14]/a")).click();
        }catch (Exception e){
            logger.error("There have some error to nevigate from Earning Summery screen to Earning Details screen.. {}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("The Earning details Page should open")
    public void theEarningDetailsPageShouldOpen() {
        try {
            wait.until(driver1 -> this.earningDetails.getEarningDetailsHeader().isDisplayed());
            logger.info("Earning Details Screen opening and the Header is {}", this.earningDetails.getEarningDetailsHeader().getText());
        }catch (Exception e){
            logger.error("Earning details page nevigation has some error.. {}",e.getMessage());
            Assert.fail();
        }
    }


    @And("click on the Return Button")
    public void clickOnTheReturnButton() {
        try{
            wait.until(driver1 -> this.earningSummeryPage.getSubmitBtn().isDisplayed());
            this.earningSummeryPage.getSubmitBtn().click();
            logger.info("Return button clicked in Earning Details Screen");
        }catch (Exception e){
            logger.error("Something wrong in the return button .. {}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("the page should redirect to Earning Summery Screen")
    public void thePageShouldRedirectToEarningSummeryScreen() {
        try{
            wait.until(driver1 -> this.earningSummeryPage.getEarningSummeryHeading().isDisplayed());
            String heading = this.earningSummeryPage.getEarningSummeryHeading().getText();
            logger.info("The Loaded Page heading is {}",heading);
        }catch (Exception e){
            logger.error("Something wrong in the page .. {}",e.getMessage());
            Assert.fail();
        }
    }
}
