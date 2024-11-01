package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;

public class BrandSectionStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    DssPage dssPage;
    WebDriverWait wait ;
    WebElement brandEle;
    String GMtitleactual = "DEDICATED EXCLUSIVE GM AS OF";
    String GMDescriptionActual= "Applicable for RE completed dealers only; Must have dedicated exclusive GM in place by 25th of prior month";
    private static final Logger logger = LoggerFactory.getLogger(BrandSectionStep.class);
    public BrandSectionStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        actions=new Actions(driver);
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        wait =  new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Then("Verify the title and description of each element inside brand section.")
    public void verifyTheTitleAndDescriptionOfEachElementInsideBrandSction() {
        try{
            wait.until(driver1 -> dssPage.getBrandSection().isDisplayed());
            brandEle = dssPage.getBrandSection();
            String GMtitle = brandEle.findElement(By.id("dedicatedExclusiveGMHeader")).getText().substring(0,28);
            String GMdescrip = brandEle.findElement(By.id("dedicatedExclusiveGMDescription")).getText();
            Assert.assertEquals(GMtitleactual,GMtitle);
            Assert.assertEquals(GMDescriptionActual,GMdescrip);
            logger.info("Brand Section is Verified.");
        }catch (Exception e){
            logger.error("GMSection is not verified..",e);
        }
    }

    @And("Check the collapse button will work properly.")
    public void checkTheCollapseButtonWillWorkProperly() {
        try{
            actions.moveToElement(brandEle.findElement(By.id("brandCommitmentAnchor"))).perform();
            actions.moveToElement(brandEle.findElement(By.id("brandCommitmentAnchor"))).perform();
            logger.info("Collapse button is working correctly.");
        }catch(Exception e){
            logger.error("Some error occured..",e);
        }
    }

    @Then("Verify the {string} present inside the dealer financial statement.")
    public void verifyThePresentInsideTheDealerFinancialStatement(String actualNote) {
        try{
            String note = brandEle.findElement(By.id("dfsNote")).getText().split("\n")[0];
            System.out.println(note);
            Assert.assertEquals(actualNote,note);
            logger.info("DFS Note is comming correctly.");
        }
        catch (Exception e){
            Assert.fail();
            logger.error("Note is mismatching.",e);
        }
    }


}
