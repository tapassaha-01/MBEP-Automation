package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.interrait.PageObjects.EarningDetails;
import org.interrait.PageObjects.EarningSummeryPage;
import org.interrait.PageObjects.GenericObjectPage;
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
import java.util.Map;

public class GenericObjectStep {
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    WebDriverWait wait;
    Select select;
    GenericObjectPage genericObjectPage;
    JavascriptExecutor js;

    private static final Logger logger = LoggerFactory.getLogger(GenericObjectStep.class);
    public GenericObjectStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        actions=new Actions(driver);
        js = (JavascriptExecutor) driver;
        genericObjectPage = this.contextSetup.getPageObjectManager().getGenericObjectPage();
        wait =  new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Then("navigate to the Generic Objective Screen")
    public void navigateToTheGenericObjectiveScreen() {
        try{
            Thread.sleep(2000);
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[5]/a")).isDisplayed());
            actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[5]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[5]/ul/li[7]/a")).click();
            this.genericObjectPage.getGenericObjectiveScreen().click();
            Thread.sleep(5000);
        }catch (Exception e){
            logger.error("Having some error to navigate earning details screen.. {}",e.getMessage());

        }
    }

    @Then("Verify the {string}")
    public void verifyThe(String actualHeading) {
        try{
            wait.until(driver1 -> this.genericObjectPage.getHeading().isDisplayed());
            logger.info("The Page Heading is comming as : {}",this.genericObjectPage.getHeading().getText());
            Assert.assertEquals(this.genericObjectPage.getHeading().getText(),actualHeading);
        }catch (Exception e){
            logger.info("There have some error..{}",e.getMessage());
            Assert.fail();
        }
    }

    @And("Verify element present in the screen for the {string}")
    public void verifyElementPresentInTheScreenForThe(String dealerTyp) {
        try{
            js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", driver.findElement(By.id("save")));
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"ContentTable\"]/tbody")).isDisplayed());
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"ContentTable\"]/tbody/tr"));
            logger.info("These elements are coming for {} dealer",dealerTyp);
            elements.stream()
                    .map(webElement -> webElement.findElement(By.tagName("td")).getText().trim())
                    .filter(text -> !text.isEmpty()) // Filter out empty values
                    .forEach(logger::info);

        }catch (Exception e){
            logger.debug("There have some error present...{}",e.getMessage());
        }
    }

    @Then("Verify all MSPU sections should be shown N\\/A for MBEP-NC dealer")
    public void verifyAllMSPUSectionsShouldBeShownNAForMBEPNCDealer() {
        try{
            wait.until(driver1 -> driver.findElements(By.xpath("//*[@id=\"ContentTable\"]/tbody//*[contains(text(), 'MSPU')]/following::td[2]")).get(1).isDisplayed());
            List<WebElement> mspuElements = driver.findElements(By.xpath("//*[@id=\"ContentTable\"]/tbody//*[contains(text(), 'MSPU')]"));
            mspuElements.forEach(mspuElement -> {
                // Get the text of the "MSPU" element
                String mspuText = mspuElement.getText().trim();

                // Locate the following <td> element and get the placeholder text
                WebElement followingTd = mspuElement.findElement(By.xpath("following::td[2]/input"));
                String placeholderText = followingTd.getAttribute("placeholder"); // Adjust attribute if placeholder is on a child <input>

                // Log both the MSPU text and the placeholder text
                logger.info("MSPU Text: " + mspuText + ", Placeholder: " + placeholderText);
            });
        }catch (Exception e){
            logger.debug("There have some error in the placeholder {}",e.getMessage());
        }
    }

    @And("Check the alert for saving record with empty fields")
    public void checkTheAlertForSavingRecordWithEmptyFields() {
    }

//*[@id="ContentTable"]/tbody//*[contains(text(), 'MSPU')]/following::td[2]
}
