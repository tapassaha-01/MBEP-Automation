package org.interrait.StepDeffination;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.opentelemetry.api.GlobalOpenTelemetry;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.LandingPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.PageObjects.PaymentMangementPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class LRSectionStep {

//     = new testContextSetup();
public testContextSetup contextSetup;
    public WebDriver driver ;
    public WebDriverWait wait ;
    public LoginPage login;
    public LandingPage landingPage;
    public DssPage dssPage;
    DateTimeFormatter formatter ;
    Select select;
    Actions actions ;
    Map<String, Integer> monthLst = new HashMap<String, Integer>() {{
        put("JAN", 1);
        put("FEB", 2);
        put("MAR", 3);
        put("APR", 4);
        put("MAY", 5);
        put("JUN", 6);
        put("JUL", 7);
        put("AUG", 8);
        put("SEP", 9);
        put("OCT", 10);
        put("NOV", 11);
        put("DEC", 12);
    }};

    private static final Logger logger = LoggerFactory.getLogger(LRSectionStep.class);

    public LRSectionStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        dssPage = this.contextSetup.getPageObjectManager().getDssPage();
        actions=new Actions(driver);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(2));
    }



    @Then("Verify the L&R section Heading {string}")
    public void verifyTheLRSectionHeading(String headingTxt) throws InterruptedException {
        wait.until(driver->dssPage.getLRHeaing().isDisplayed());
            Assert.assertEquals(dssPage.getLRHeaing().getText(),headingTxt);


    }

    @Then("Open the MCVP section")
    public void openTheMCVPSection() throws InterruptedException {

        Assert.assertTrue(dssPage.getExpandMCVP().isDisplayed());
        actions.moveToElement(dssPage.getExpandMCVP()).perform();
        dssPage.getExpandMCVP().click();
        Thread.sleep(2000);
    }

    @When("the sectional heading as {string} with the Date {string}")
    public void theSectionalHeadingAsWithTheDate(String headingTxt, String dateFormat) {
        String mcvpHeadingTxt = dssPage.getMcvpHeader().getText();
        String Txt =  mcvpHeadingTxt.substring(0,36);
        logger.info(Txt);
        String date = mcvpHeadingTxt.substring(39,49);
        logger.info(date);
        formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try{
            LocalDate.parse(date, formatter);
            logger.info("The date is in valid MM/dd/yyyy format.");
            Assert.assertTrue(true);
        }
     catch (DateTimeParseException e) {
         logger.info("The date is NOT in valid MM/dd/yyyy format.");
         Assert.fail();

    }

    }


    @Then("Verify the MCVP subheading contains {string} sentence.")
    public void verifyTheMCVPSubheadingContainsSentence(String ActualDescription) {
        wait.until(driver->dssPage.getMCVPdescription().isDisplayed());
        actions.moveToElement(dssPage.getMCVPdescription()).perform();
        String DisplayedDescrip = dssPage.getMCVPdescription().getText();
        Assert.assertEquals(DisplayedDescrip,ActualDescription);
    }

    @And("Verify the earning month and next month status.")
    public void verifyTheEarningMonthAndNextMonthStatus() {
        if(dssPage.getMcvpCurrentMonSts().isDisplayed() && dssPage.getMcvpCurrentMonSts().isDisplayed()){
            logger.info("MCVP status are found visible.");
            actions.moveToElement(dssPage.getMcvpCurrentMonSts()).perform();
        }
        else{
            logger.error("MCVP status are not found");
            Assert.fail();
        }
    }

    @Then("Verify that MCVPDataTable contains Previous {string} + current month of data")
    public void verifyThatMCVPDataTableContainsPreviousCurrentMonthOfData(String monCount) {
        wait.until(driver->dssPage.getMcvpCurrMon().get(1).isDisplayed());
        List<WebElement> months = dssPage.getMcvpCurrMon();
        select = new Select(dssPage.getMonDrop());
        int currMonVal= monthLst.get((select.getFirstSelectedOption().getText()).toUpperCase());
        for(int i=months.size()-1;i>1;i--){
            if(currMonVal<1){
                currMonVal=12;
            }
            String currMon = months.get(i).getText();
            Assert.assertEquals(monthLst.get(currMon),currMonVal--);

        }
        logger.info("Varified the MCVPDataTable containing months.");
    }


//
    @Then("Check the calculation for {string} values with the current and previous month value.")
    public void checkTheCalculationForValuesWithTheCurrentAndPreviousMonthValue(String descrip) {
        wait.until(driver->dssPage.getInstockMinReqMonData().get(2).isDisplayed() && dssPage.getInstockMinReqMonDataActual().get(0).isDisplayed());
        String currMonInstockdata = dssPage.getInstockMinReqMonData().get(2).getText();
        String nxtMonInstockData = dssPage.getInstockMinReqMonData().get(3).getText();
        String currMonInstockdataActual = dssPage.getInstockMinReqMonDataActual().get(0).getText();
        String nxtMonInstockDataActual = dssPage.getInstockMinReqMonDataActual().get(1).getText();
        if((!currMonInstockdata.trim().isEmpty() &&!currMonInstockdataActual.trim().isEmpty())|| (!nxtMonInstockData.trim().isEmpty() && !nxtMonInstockDataActual.trim().isEmpty())){
            Assert.assertEquals(currMonInstockdata,currMonInstockdataActual);
            Assert.assertEquals(nxtMonInstockData,nxtMonInstockDataActual);
            logger.info("MCVP Instock minimum calculation is correct.");
        }
        else{
            logger.error("Having calculation error !!");
        }
        if(dssPage.getInstockMinReqMonData().get(0).isDisplayed()){
            Assert.assertEquals(dssPage.getInstockMinReqMonData().get(0).getText(),descrip);
            logger.info("Instock minimum row name is comming correctly.");
        }
        else{
            logger.error("Having Instock minimum row name problem!!");
        }

    }
//
    @Then("Verify the MCVP website link is redirecting to {string}")
    public void verifyTheMCVPWebsiteLinkIsRedirectingTo(String linkActual) {
        wait.until(driver->dssPage.getMCVPfooterLink().isDisplayed());
        String link = dssPage.getMCVPfooterLink().getAttribute("href");
        Assert.assertEquals(linkActual,link);
        logger.info("MCVP link is comming correctly.");
    }

    @Then("verify that {string} coming under L&R Pre-Qualifier.")
    public void verifyThatMSSComingUnderLRPreQualifier(String mssHeading) {

        WebElement MSS;
        try {

            actions.moveToElement(dssPage.getLREarningElement()).perform();
            wait.until(driver->dssPage.getLRBasequalifer().findElement(By.xpath("//*[@id=\"base_qualifier_loyalityAndRetentionDiv\"]/div/div[3]")).isDisplayed());

            MSS = dssPage.getLRBasequalifer().findElement(By.xpath("//*[@id=\"base_qualifier_loyalityAndRetentionDiv\"]/div/div[3]"));
            if(MSS.isDisplayed()){
                Assert.assertEquals(MSS.findElement(By.id("mss_header2024")).getText().substring(0,29),mssHeading);
                logger.info("MSS Section is coming under L&R section");
            }
        }catch (Exception e){
            logger.error("MSS Section is not coming under L&R Pre-Qualifer");
            Assert.fail();
        }


    }

    @And("{string} is coming under L&R Earning Elements.")
    public void mspuIsComingUnderLREarningElements(String mspuHeading) {
        WebElement MSPU;
        try {
            wait.until(driver->dssPage.getLREarningElement().findElement(By.id("MSPU_div")).isDisplayed());
            MSPU = dssPage.getLREarningElement().findElement(By.id("MSPU_div"));
            if(MSPU.isDisplayed()){
                Assert.assertEquals(MSPU.findElement(By.id("MSPUHeader")).getText().substring(0,33),mspuHeading);
                logger.info("MSPU Section is coming under L&R section");
            }
        }catch (Exception e){
            logger.error("MSPU Section is not coming under L&R Earning Element");
            Assert.fail();
        }
    }


    @Then("Percentile value should match with the Obj min value.")
    public void percentileValueShouldMatchWithTheObjMinValue() {
        try{actions.moveToElement(dssPage.getLREarningElement()).perform();
        wait.until(driver ->
                dssPage.getMspuObjVal().isDisplayed() && dssPage.getMspuDescrip().isDisplayed()
        );
        String mspuObjValActual = dssPage.getMspuObjVal().getText();
        String mspuObjValue = dssPage.getMspuDescrip().getText().substring(13,15);
        Assert.assertEquals(mspuObjValActual,mspuObjValue);
        logger.info("MSPU Objective Value is verified");}
        catch (Exception e){
            logger.error("MSPU Objective Value is not verified..",e);
            Assert.fail();
        }
    }

    @And("verify the MSPU link {string}.")
    public void verifyTheMSPULink(String link) {
        try{actions.moveToElement(dssPage.getLREarningElement()).perform();
            wait.until(driver->dssPage.getMSPUlink().isDisplayed());

            String mspuActualLink = dssPage.getMSPUlink().getAttribute("href");
            Assert.assertEquals(mspuActualLink,link);
            logger.info("MSPU link is verified");
        }
        catch (Exception e){
            logger.error("MSPU link is not verified",e);
            Assert.fail();
        }
    }

    @Then("Verify the {string} and {string}.")
    public void verifyTheAnd(String heading, String subheading) {
        try{
            wait.until(driver1 -> dssPage.getLREarningElement().findElement(By.id("fyslHeader")).isDisplayed() &&
            dssPage.getLREarningElement().findElement(By.id("fyslDescription")).isDisplayed());
            String actualHeading = dssPage.getLREarningElement().findElement(By.id("fyslHeader")).getText().substring(0,27);
            String actualSubHeading = dssPage.getLREarningElement().findElement(By.id("fyslDescription")).getText();
            Assert.assertEquals(actualHeading,heading);
            Assert.assertEquals(actualSubHeading,subheading);
            logger.info("Earning Element FSL Heading and subheading is verified successfully");
        }catch (Exception e){
            logger.error("Having error to verify header and subheading",e);
            Assert.fail();
        }
    }

    @And("FSL Percentile value should match with Obj min value.")
    public void fslPercentileValueShouldMatchWithObjMinValue() {
        try{
            wait.until(driver1 -> dssPage.getLREarningElement().findElement(By.id("fyslDescription")).isDisplayed()&&
                    dssPage.getLREarningElement().findElement(By.id("fyslObjMinVal")).isDisplayed());
            Assert.assertEquals(dssPage.getLREarningElement().findElement(By.id("fyslDescription")).getText().substring(13,15),dssPage.getLREarningElement().findElement(By.id("fyslObjMinVal")).getText());
        logger.info("Percentile value is verified with the Obj Minimum value.");
        }catch (Exception e){
            logger.error("Having error to verify the Objective minimum value.",e);
            Assert.fail();
        }
    }
}
