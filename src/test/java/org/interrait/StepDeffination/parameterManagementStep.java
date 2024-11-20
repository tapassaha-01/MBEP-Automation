package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.interrait.PageObjects.DealerEarningStatement;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.PageObjects.ParameterManagement;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class parameterManagementStep{
    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    WebDriverWait wait;
    ParameterManagement parameterManagement;
    String sectionId = "";
    JavascriptExecutor js;
    Map<String,String> SectionId = new HashMap<String,String>(){{
        put("MBEP SET PAYOUT PERCENTAGE section","Mbep" );
        put("MBEP-HI SET PAYOUT PERCENTAGE","MbepHI" );
        put("MBEP-NC SET PAYOUT","MbepNC" );
        put("MBEP-NC/MSEP SET SALES PAYOUT","Msep" );
    }};



    private static final Logger logger = LoggerFactory.getLogger(parameterManagementStep.class);
    public parameterManagementStep(testContextSetup contextSetup) throws IOException {
        this.contextSetup = contextSetup;
        driver = this.contextSetup.getTestBase().webDriverManager();
        parameterManagement = this.contextSetup.getPageObjectManager().getParameterManagement();
        actions=new Actions(driver);
        js = (JavascriptExecutor) driver;
        wait =  new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Then("navigate to the parameter management screen")
    public void navigateToTheParameterManagementScreen() {
        try{
            Thread.sleep(2000);
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[5]/a")).isDisplayed()); //*[@id="cssmenu"]/ul/li[5]/a
            actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[5]/a"))).perform();
            driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[5]/ul/li[4]/a")).click();
            actions.moveToElement(this.parameterManagement.getParameterManagementScreen()).click();
            Thread.sleep(5000);
        }catch (Exception e){
            logger.error("Having some error to navigate earning details screen.. {}",e.getMessage());

        }
    }


    @And("Verify the elements present in MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section for the {string} and {string} and {string}")
    public void verifyTheElementsPresentInMBEPMBEPHISETNONBRANDPAYOUTPERCENTAGESectionForTheAnd(String mon, String quater,String year) {
        try{
            Thread.sleep(2000);
            wait.until(driver1 -> driver.findElement(By.id("inputPayoutQuarter")).isDisplayed()&&
                    driver.findElement(By.id("inputPayoutMonth")).isDisplayed());
            driver.findElement(By.id("inputPayoutYear")).sendKeys(year);
            driver.findElement(By.id("inputPayoutQuarter")).sendKeys(quater);
            driver.findElement(By.id("inputPayoutMonth")).sendKeys(mon);
                driver.findElement(By.id("btnFetch")).click();

            List<WebElement> elements = this.parameterManagement.getMBEP_MBEP_BrandPayoutElement();
            logger.debug("elements present in the MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section.");
            String name ="";
            for(WebElement webElement:elements){
                WebElement cellElement = webElement.findElement(By.tagName("td"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", cellElement);
                name = cellElement.getText();
                if(!name.isEmpty()){
                logger.info(name);}
            }
            Thread.sleep(2000);
        }catch (Exception e){
            logger.debug("There have some error...{}",e.getMessage());
            Assert.fail();
        }
    }

    @And("Verify the Heading is coming as {string}")
    public void verifyTheHeadingIsComingAs(String heading) {
        try {
            wait.until(driver1 -> driver.findElement(By.id("addTrackingInBodyTittle")).isDisplayed());
            String actualHeading  = driver.findElement(By.id("addTrackingInBodyTittle")).getText().trim();
            logger.info("The Heading is coming as :{}",actualHeading);
            Assert.assertEquals(actualHeading,heading);

        }catch (Exception e){
            logger.error("Have some error in fetching heading..{}",e.getMessage());
            Assert.fail();
        }
    }
    @And("Verify the elements present inside MBEP SET PAYOUT PERCENTAGE")
    public void verifyTheElementsPresentInsideMBEPSETPAYOUTPERCENTAGE() {
        try {
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"Mbep\"]/tbody/tr[3]")).isDisplayed());
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"Mbep\"]/tbody/tr"));
            logger.info("The elements present inside MBEP SET PAYOUT PERCENTAGE are :");
            String name="";
            for(int i=2;i<elements.size()-1;i++){
                name = elements.get(i).findElements(By.tagName("td")).get(0).getText();
                if(!name.isEmpty()){
                logger.info(name);
            }}
        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("Verify the elements present inside MBEP-HI Set Payout Section")
    public void verifyTheElementsPresentInsideMBEPHISetPayoutSection() {
        try {
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"MbepHI\"]/tbody")).isDisplayed());
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"MbepHI\"]/tbody/tr"));
            logger.info("The elements present inside MBEP-HI Set Payout Section are :");
            String name="";
            for(int i=2;i<elements.size()-1;i++){
                WebElement cellElement = elements.get(i).findElements(By.tagName("td")).get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", cellElement);
                Thread.sleep(200);
                name = cellElement.getText();
                if(!name.isEmpty()){
                    logger.info(name);
                }}
        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
    }

    @And("Verify the elements present inside MBEP-NC Set Payout Section")
    public void verifyTheElementsPresentInsideMBEPNCSetPayoutSection() {
        try {
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"MbepNC\"]/tbody")).isDisplayed());
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"MbepNC\"]/tbody/tr"));
            logger.info("The elements present inside MBEP-NC Set Payout Section are :");
            String name="";
            for(int i=2;i<elements.size()-1;i++){
                WebElement cellElement = elements.get(i).findElements(By.tagName("td")).get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", cellElement);
                Thread.sleep(200);
                name = cellElement.getText();
                if(!name.isEmpty()){
                    logger.info(name);
                }}
        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
        
    }

    @And("Verify the elements present inside MBEP-NC\\/MSEP Set sales payout Section")
    public void verifyTheElementsPresentInsideMBEPNCMSEPSetSalesPayoutSection() {
        try {
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"Msep\"]/tbody")).isDisplayed());
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"Msep\"]/tbody/tr"));
            logger.info("The elements present inside MBEP-NC/MSEP SET SALES PAYOUT Section are :");
            String name="";
            for(int i=2;i<elements.size()-1;i++){
                WebElement cellElement = elements.get(i).findElements(By.tagName("td")).get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", cellElement);
                Thread.sleep(200);
                name = cellElement.getText();
                if(!name.isEmpty()){
                    logger.info(name);
                }}
        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
        
    }


    @And("Verify the elements present inside Set Processing Parameters")
    public void verifyTheElementsPresentInsideSetProcessingParameters() {
        try {
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"defaultBody\"]/div/div/div[3]/table[2]/tbody")).isDisplayed());//*[@id="defaultBody"]/div/div/div[3]/table[2]/tbody/tr
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"defaultBody\"]/div/div/div[3]/table[2]/tbody/tr"));
            logger.info("The elements present inside Set Processing Parameters Section are :");
            String name="";
            for(int i=2;i<elements.size()-1;i++){
                WebElement cellElement = elements.get(i).findElements(By.tagName("td")).get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", cellElement);
                Thread.sleep(200);
                name = cellElement.getText();
                if(!name.isEmpty()){
                    logger.info(name);
                }}
        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("^Set the \"([^\"]*)\" for \"([^\"]*)\"$")
    public void setTheFor(String values, String section) {
        String[] valueArr = values.split(",");

        sectionId = SectionId.get(section);

        try {

            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\""+sectionId+"\"]/tbody")).isDisplayed());
            logger.info("Below values are set....");
            WebElement first = driver.findElement(By.xpath("//*[@id=\""+sectionId+"\"]/tbody/tr[3]/td[3]/input"));
            for (String s : valueArr) {
                assert first != null;
                first.sendKeys(s);
                first.sendKeys(Keys.TAB);
                first = (WebElement) js.executeScript("return document.activeElement;");
                Thread.sleep(500);
                logger.info(s);
            }
//            js.executeScript("document.getElementById('quaterlyPayoutBtnSet').click();");
            assert first != null;
            first.sendKeys(Keys.ENTER);
            Thread.sleep(2000);

            Alert alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(5000);
            alert = driver.switchTo().alert();
            alert.accept();
            Thread.sleep(2000);

        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
    }


    @Then("^Verify the updated values with \"([^\"]*)\"$")
    public void verifyTheUpdatedValuesWith(String oldValues) {
        String[] values = oldValues.split(",");
        try {
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\""+sectionId+"\"]/tbody/tr[3]")).isDisplayed());
            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\""+sectionId+"\"]/tbody/tr"));
            String name="",value="";
            int ind=0;
            for(int i=2;i<elements.size()-1;i++){
                value = elements.get(i).findElements(By.tagName("td")).get(1).getText();
                name = elements.get(i).findElements(By.tagName("td")).get(0).getText();
                if(!name.isEmpty()){
                    logger.info("The input value for {} is {} : and showing {} ",name,value,values[ind++]);
                }}
        }catch (Exception e){
            logger.error("Have some error in fetching elements..{}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("Select {string}, {string} and {string}")
    public void selectAnd(String year, String quater, String mon) {
        try{
//            Thread.sleep(2000);
            wait.until(driver1 -> driver.findElement(By.id("inputPayoutQuarter")).isDisplayed()&&
                    driver.findElement(By.id("inputPayoutMonth")).isDisplayed());
            driver.findElement(By.id("inputPayoutYear")).sendKeys(year);
            driver.findElement(By.id("inputPayoutQuarter")).sendKeys(quater);
            driver.findElement(By.id("inputPayoutMonth")).sendKeys(mon);
        }catch (Exception e){
            logger.error("There have some error while try to retrieve the mon year data..{}",e.getMessage());
            Assert.fail();
        }
    }

    @And("Click on Retrieve button")
    public void clickOnRetriveButton() {
        try{
            wait.until(driver1 -> driver.findElement(By.id("btnFetch")).isDisplayed());
            driver.findElement(By.id("btnFetch")).click();
            Thread.sleep(2000);
        }catch (Exception e){
            logger.error("Having error while click on retrieve button..{}",e.getMessage());
            Assert.fail();
        }
    }

    @Then("fill {string} to the {string} in MBEP & MBEP-HI SET NON BRAND PAYOUT PERCENTAGE section")
    public void fillToTheInMBEPMBEPHISETNONBRANDPAYOUTPERCENTAGESection(String value, String field) {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[contains(text(), '"+field+"')]//following::input")).isDisplayed());
            WebElement ele = driver.findElement(By.xpath("//*[contains(text(), '"+field+"')]//following::input"));
                    ele.sendKeys(value);
            ele.sendKeys(Keys.TAB);

            Thread.sleep(2000);
        }catch (Exception e){
            logger.info("There have some error in checking the alert..{}",e.getMessage());
            Assert.fail();
        }
    }

    @And("observe the {string}")
    public void observeThe(String alertMsg) {
        try{
            if(this.parameterManagement.isAlertPresent(driver)){
                Alert alert = driver.switchTo().alert();
                String msg = alert.getText();
                logger.info("The alert massage coming as : {}",msg);
                Assert.assertEquals(msg,alertMsg);

            }else{
                logger.info("Alert is not coming");
                Assert.fail();
            }

        }catch (Exception e){
            logger.debug("There have some error in alert..{}",e.getMessage());

        }


    }

    @Then("fill value {string} to the {string} in the  MBEP NC SET PAYOUT PERCENTAGE section")
    public void fillValuesToTheInTheMBEPNCSETPAYOUTPERCENTAGESection(String value, String input_field) {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[contains(text(), '"+input_field+"')]//following::input")).isDisplayed());
            WebElement ele = driver.findElement(By.xpath("//*[contains(text(), '"+input_field+"')]//following::input"));
                    ele.sendKeys(value);
            ele.sendKeys(Keys.TAB);
            Thread.sleep(200);
        }catch (Exception e){
            logger.info("There have some error in checking the alert..{}",e.getMessage());
            Assert.fail();
        }

    }
}
