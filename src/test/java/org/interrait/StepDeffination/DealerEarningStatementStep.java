package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.interrait.PageObjects.DealerEarningStatement;
import org.interrait.PageObjects.DssPage;
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
import java.util.List;

public class DealerEarningStatementStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    WebDriverWait wait;
    DealerEarningStatement dealerEarningStatement;
    Select select;


    private static final Logger logger = LoggerFactory.getLogger(DealerEarningStatementStep.class);
    public DealerEarningStatementStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        actions=new Actions(driver);
        dealerEarningStatement = this.contextSetup.getPageObjectManager().getDealerEarningStatement();
        wait =  new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Then("Click on the DealerEarningStatement from the menu")
    public void clickOnTheDealerEarningStatementFromTheMenu() {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a")).isDisplayed());
            actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[6]/a")).click();
            actions.moveToElement(this.dealerEarningStatement.getStatementMenu()).click();
            Thread.sleep(5000);
        }catch (Exception e){
            logger.error("Having some error to navigate earning details screen.. {}",e.getMessage());
            Assert.fail();
        }
    }

    @And("Verify the Heading of the screen")
    public void verifyTheHeadingOfTheScreen() {
        try{
            wait.until(driver1 -> this.dealerEarningStatement.getPageHeading().isDisplayed());
            logger.info("The Page Heading is : {}",this.dealerEarningStatement.getPageHeading().getText());
        }catch (Exception e){
            logger.error("Page Heading has some error..{}",e.getMessage());}
    }

    @Then("Search {string}")
    public void search(String dealerCd) {
        wait.until(driver1 -> this.dealerEarningStatement.getSearchDealer().isDisplayed());
        this.dealerEarningStatement.getSearchDealer().sendKeys(dealerCd);
    }

    @And("Select date-range from {string} to {string} and submit")
    public void selectDateRangeFromToAndSubmit(String startMon, String endMon) throws InterruptedException {
        try{
            Thread.sleep(5000);
        wait.until(driver1 -> this.dealerEarningStatement.getStartMonDropDown().isDisplayed()&&
                this.dealerEarningStatement.getEndMonDropDown().isDisplayed());
        select = new Select(this.dealerEarningStatement.getStartMonDropDown());
        select.selectByValue(startMon);
        select = new Select(this.dealerEarningStatement.getEndMonDropDown());
        select.selectByValue(endMon);
        this.dealerEarningStatement.getSubmitBtn().click();
        Thread.sleep(4000);}catch (Exception e){
            logger.debug("There have some error selecting mon...{}",e.getMessage());
        }
    }

    @Then("verify the table column names")
    public void verifyTheTableColumnNames() {
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;


            wait.until(driver1 -> this.dealerEarningStatement.getTableHeadings().get(2).isDisplayed());
            List<WebElement> headerList = this.dealerEarningStatement.getTableHeadings();
            logger.info("These are the table headings....");
            for(int i=0;i<headerList.size();i++){
                if(i==headerList.size()/2){
                  js.executeScript("arguments[0].scrollLeft = arguments[1];", driver.findElement(By.xpath("//*[@id=\"area_code_wrapper\"]/div[2]/div[2]")),i);
                }
                logger.info(headerList.get(i).getText());

            }
//            headerList.forEach(webElement -> );
        }catch (Exception e){
            logger.debug("There have some error to get the table headings...{}",e.getMessage());
        }

    }
}
