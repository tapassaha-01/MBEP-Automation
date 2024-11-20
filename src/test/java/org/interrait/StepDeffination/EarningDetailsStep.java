package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
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

public class EarningDetailsStep {

    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    WebDriverWait wait;
    DssPage dssPage;
    JavascriptExecutor js;
    EarningDetails earningDetails;
    EarningSummeryPage earningSummeryPage;
    Map<String, Integer> monthLst;


    private static final Logger logger = LoggerFactory.getLogger(EarningDetailsStep.class);

    public EarningDetailsStep(testContextSetup contextSetup) throws IOException {
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

    @Then("navigate to the Earning Details Screen")
    public void navigateToTheEarningDetailsScreen() {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a")).isDisplayed());
            actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[4]/a")).click();
            actions.moveToElement(this.earningDetails.getEarningDetailsMenu()).click();
            Thread.sleep(5000);
        }catch (Exception e){
            logger.error("Having some error to navigate earning details screen.. {}",e.getMessage());
            Assert.fail();
        }

    }

    @And("search with a {string} and submit")
    public void searchWithAAndSubmit(String dlrCd) {
        try{
            wait.until(driver1 -> driver.findElement(By.id("searchDealerCode")).isDisplayed());
            driver.findElement(By.id("searchDealerCode")).sendKeys(dlrCd);
            driver.findElement(By.id("submit")).click();
            Thread.sleep(4000);
            logger.info("dealer has searched in Earning Details screen..");
        }catch (Exception e){
            logger.error("Having error to load Earning details screen..{}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("calculate the total value for the {string}")
    public void calculateTheTotalValueForThe(String mon) {
//take the dynamic size from the UI
        int monthInd=monthLst.get(mon)+1;
        try{
            wait.until(driver1 -> this.earningDetails.getTableMonHeader().get(1).isDisplayed());
//            monthInd = IntStream.range(2, this.earningDetails.getTableMonHeader().size())
//                    .filter(i -> this.earningDetails.getTableMonHeader().get(i).getText().equals(mon))
//                    .findFirst()
//                    .orElse(-1); // or any default value you prefer
            List<WebElement> tableContent = driver.findElements(By.xpath("//*[@id=\"area_code\"]/tbody/tr"));
            int total=0;
            for(int i=2;i<tableContent.size();i++){
                if(i==9){
                    continue;
                }
                String val = driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody/tr["+i+"]/td["+monthInd+"]")).getText();

                if(val.equals("N/A")){continue;}
                total+=Integer.parseInt(val.replaceAll("[^0-9]", ""));
            }
            WebElement Total = driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody/tr["+tableContent.size()+"]/td["+monthInd+"]"));
            int actualTotal = Integer.parseInt((Total).getText().replaceAll("[^0-9]", ""));
            js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", Total);
            logger.info("The calculated total is : {} and the actual total is : {}",total,actualTotal);
            Assert.assertEquals(actualTotal,total);

        }catch (Exception e){
            logger.error("There have some error..{}",e.getMessage());
            Assert.fail();
        }
    }
}
