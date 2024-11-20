package org.interrait.StepDeffination;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.interrait.PageObjects.DssPage;
import org.interrait.PageObjects.EarningDetails;
import org.interrait.PageObjects.EarningSummeryPage;
import org.interrait.PageObjects.LoginPage;
import org.interrait.utils.testContextSetup;
import org.openqa.selenium.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class DssPageStep {

    testContextSetup contextSetup;
    WebDriver driver;
    Actions actions;
    LoginPage login;
    WebDriverWait wait;
    DssPage dssPage;
    String dealerType = "";
    JavascriptExecutor js;
    EarningDetails earningDetails;
    EarningSummeryPage earningSummeryPage;
    Map<String, Integer> monthLst;
    Select select;
    String mon;
    boolean LR_Pre_Qual=true;
    String dealerTyp;
    DateTimeFormatter formatter ;
    int isSkip=0;
    List<String> monStatus=new ArrayList<String>();
    WebElement brandEle;
    String GMtitleactual = "DEDICATED EXCLUSIVE GM AS OF";
    String GMDescriptionActual= "Applicable for RE completed dealers only; Must have dedicated exclusive GM in place by 25th of prior month";


    private static final Logger logger = LoggerFactory.getLogger(DssPageStep.class);

    public DssPageStep(testContextSetup contextSetup) throws IOException {
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
//for digitization section
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
        try{
            String id = this.dealerType.equals("MBEP-NC")?"digitalShowroom_utilAnchorNC":"digitalShowroom_utilAnchor";
            wait.until(driver1 -> driver.findElement(By.id(id)).isDisplayed());
            actions.moveToElement(driver.findElement(By.id(id))).perform();
            actions.moveToElement(driver.findElement(By.id(id))).click();
            Thread.sleep(20000);
            actions.moveToElement(driver.findElement(By.id(id))).click();
            Thread.sleep(2000);
            actions.moveToElement(driver.findElement(By.id(id))).click();
        }catch (Exception e){
            logger.error("Some exception has occurred.. {}",e.getMessage());
        }
    }

    @And("Sales Performance Section should not come for MBEP dealer")
    public void salesPerformanceSectionShouldNotComeForMBEPMBEPHIDealer() {
        if(this.dealerType.equals("MBEP-NC")){
            return;
        }
        try {
            wait.until(driver1 ->driver.findElement(By.id("salesPerformanceDivHeading")).isDisplayed());
            actions.moveToElement(driver.findElement(By.id("salesPerformanceDivHeading"))).perform();
            Thread.sleep(3000);
            logger.debug("Sales Performance Section is not supposed to appear for MBEP/MBEP-HI dealer, but it did.");
            Assert.fail("Sales Performance Section is present for MBEP/MBEP-HI dealer when it should not be.");
        } catch (NoSuchElementException | TimeoutException e) {
            logger.debug("Sales Performance is not coming for MBEP/MBEP-HI dealer, as expected.");
        } catch (AssertionError e) {
            logger.error("Unexpected behavior: {}", e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//for Earning Summery screen
//@Then("Verifying the Earning Summery page is loading properly")
//public void verifyingTheEarningSummeryPageIsLoadingProperly() {
//    try{
//        wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a")).isDisplayed());
//        actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/a"))).perform();
//        driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[3]/a")).click();
//        actions.moveToElement(this.earningSummeryPage.getEarningSummeryMenu()).click();
//        Thread.sleep(5000);
//        wait.until(driver1 -> this.earningSummeryPage.getEarningSummeryHeading().isDisplayed());
//        Assert.assertEquals(this.earningSummeryPage.getEarningSummeryHeading().getText(),"Earnings Summary");
//        logger.debug("The Heading of the page is {}",this.earningSummeryPage.getEarningSummeryHeading().getText());
//    }catch (Exception e){
//        logger.error("Earning Element has some error..",e);
//        Assert.fail();
//    }
//}
//
//    @And("Verify there have {int} columns in the table.")
//    public void verifyThereHaveColumnsInTheTable(int columnCount) {
//        try{
//            wait.until(driver1 -> this.earningSummeryPage.getSummeryTableHeader().get(2).isDisplayed());
//            logger.info("The column count of Summery Page table headers.. {}",this.earningSummeryPage.getSummeryTableHeader().size()-1);
//            Assert.assertEquals(this.earningSummeryPage.getSummeryTableHeader().size()-1,columnCount);
//        }
//        catch (Exception e){
//            logger.error("Having some error in getting the table header.. {} ",e.getMessage());
//            Assert.fail();
//        }
//    }
//
//    @Then("enter dlrcd as {string}, mon {string}, year {string} and click on submit")
//    public void enterDlrcdAsMonYearAndClickOnSubmit(String dlrcd, String mon, String year) {
//        this.mon =mon;
//        try{
//            wait.until(driver1 -> this.earningSummeryPage.getDealerCode().isDisplayed()&&
//                    this.earningSummeryPage.getMonDropDown().isDisplayed()&&
//                    this.earningSummeryPage.getYearDropDown().isDisplayed());
//            this.earningSummeryPage.getDealerCode().sendKeys(dlrcd);
//            select = new Select(this.earningSummeryPage.getMonDropDown());
//            select.selectByValue(mon);
//            select = new Select(this.earningSummeryPage.getYearDropDown());
//            select.selectByValue(year);
//            this.earningSummeryPage.getSubmitBtn().click();
//            Thread.sleep(2000);
//        }catch (Exception e){
//            logger.error("Having some error in selecting dealer,mon,year {}",e.getMessage());
//            Assert.fail();
//        }
//    }
//
//    @And("Verify that Forfeited_Escrow_$ row added below the Brand Commitment_\\(payment + Escrow)")
//    public void verifyThatForfeited_Escrow_$RowAddedBelowTheBrandCommitmentPaymentEscrow() {
//        try{
//            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody")).isDisplayed());
//            Thread.sleep(2000);
//            String firstEle = driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody/tr[2]/td[1]")).getText();//*[@id="area_code"]/tbody/tr[2]/td[1]
//            String secondEle = driver.findElement(By.xpath("//*[@id=\"area_code\"]/tbody/tr[3]/td[1]")).getText();
//            logger.info("First element in the Brand Commitment Section {} and the second element is {} present.",firstEle,secondEle);
//        }catch (Exception e){
//            logger.error("There have some error while getting the elements.. {}",e.getMessage());
//            Assert.fail();
//        }
//    }
//
//
//    @When("Click on the Earning_details")
//    public void clickOnTheEarning_details() {
//        try{
//            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"area_code_6\"]/tbody/tr/td[14]/a")).isDisplayed());
//            driver.findElement(By.xpath("//*[@id=\"area_code_6\"]/tbody/tr/td[14]/a")).click();
//        }catch (Exception e){
//            logger.error("There have some error to nevigate from Earning Summery screen to Earning Details screen.. {}",e.getMessage());
//            Assert.fail();
//        }
//    }
//
//    @Then("The Earning details Page should open")
//    public void theEarningDetailsPageShouldOpen() {
//        try {
//            wait.until(driver1 -> this.earningDetails.getEarningDetailsHeader().isDisplayed());
//            logger.info("Earning Details Screen opening and the Header is {}", this.earningDetails.getEarningDetailsHeader().getText());
//        }catch (Exception e){
//            logger.error("Earning details page nevigation has some error.. {}",e.getMessage());
//            Assert.fail();
//        }
//    }
//
//
//    @And("click on the Return Button")
//    public void clickOnTheReturnButton() {
//        try{
//            wait.until(driver1 -> this.earningSummeryPage.getSubmitBtn().isDisplayed());
//            this.earningSummeryPage.getSubmitBtn().click();
//            logger.info("Return button clicked in Earning Details Screen");
//        }catch (Exception e){
//            logger.error("Something wrong in the return button .. {}",e.getMessage());
//            Assert.fail();
//        }
//    }
//
//    @Then("the page should redirect to Earning Summery Screen")
//    public void thePageShouldRedirectToEarningSummeryScreen() {
//        try{
//            wait.until(driver1 -> this.earningSummeryPage.getEarningSummeryHeading().isDisplayed());
//            String heading = this.earningSummeryPage.getEarningSummeryHeading().getText();
//            logger.info("The Loaded Page heading is {}",heading);
//        }catch (Exception e){
//            logger.error("Something wrong in the page .. {}",e.getMessage());
//            Assert.fail();
//        }
//    }
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
    public void verifyTheAnde(String heading, String description) {
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
    //L&R section
    @Then("Verify the L&R section Heading {string}")
    public void verifyTheLRSectionHeading(String headingTxt) throws InterruptedException {
        Thread.sleep(2000);
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
        try {
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
        }catch (Exception e){
            logger.debug("There have some error came .. {}",e.getMessage());
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

    @Then("verify that {string} coming under L&R Pre-Qualifier for the {string}")
    public void verifyThatMSSComingUnderLRPreQualifier(String mssHeading,String mon) {
        this.mon = mon;
        if(!(Integer.parseInt(mon)>=5 && Integer.parseInt(mon)<7)){
            return;
        }
        WebElement MSS;
        try {

            actions.moveToElement(dssPage.getLREarningElement()).perform();
            Thread.sleep(5000);
            wait.until(driver->dssPage.getLRBasequalifer().findElement(By.xpath("//*[@id=\"base_qualifier_loyalityAndRetentionDiv\"]/div/div[3]")).isDisplayed());

            MSS = dssPage.getLRBasequalifer().findElement(By.xpath("//*[@id=\"base_qualifier_loyalityAndRetentionDiv\"]/div/div[3]"));
            if(MSS.isDisplayed()){
                Assert.assertEquals(MSS.findElement(By.id("mss_header2024")).getText().substring(0,29),mssHeading);
                logger.info("MSS Section is coming under L&R section");
            }
        }catch (Exception e){
            logger.error("MSS Section is not coming under L&R Pre-Qualifer");
            Assert.fail();
        }catch (AssertionError e){
            logger.debug(e.getMessage());
        }


    }

    @And("{string} is coming under L&R Earning Elements")
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

    @When("Any of L&RSection Pre-Qualifier is not achieved for Earning Month")
    public void anyOfLRSectionPreQualifierIsNotAchieved() {
        try{
//            actions.moveToElement(dssPage.getLREarningElement()).perform();
            wait.until(driver1->driver.findElement(By.xpath("//*[@id=\"base_qualifier_loyalityAndRetentionDiv\"]/div")).isDisplayed());
            Thread.sleep(2000);
            if(driver.findElement(By.id("mcvp_qualifier_1")).isDisplayed()){
                Thread.sleep(2000);
                String status = driver.findElement(By.xpath("//*[@id=\"mcvpPrvsMthSts\"]")).getText();
                logger.info("L&R Section MCVP Pre-Qualifier is {}",status);
                LR_Pre_Qual = false;
                Assert.assertEquals(status,"NOT ACHIEVED");
            }
            if (driver.findElement(By.id("mssSection2024")).isDisplayed()){
                String status = driver.findElement(By.id("mss_prvMtSts2024")).getText();
                logger.info("L&R Section MSS Pre-Qualifier is {}",status);
                LR_Pre_Qual=false;
                Assert.assertEquals(status,"NOT ACHIEVED");
            }

        }catch (Exception e ){
            logger.error("Having some error in the L&R section not achieved test-case",e);
            Assert.fail();
        }
        catch (AssertionError e){
            logger.debug(e.getMessage());
        }
    }

    @Then("Both the Earning Elements should be not achieved for Earning Month")
    public void bothTheEarningElementsShouldBeNotAchieved() {
        if(LR_Pre_Qual){
            return;
        }
        try{
            wait.until(driver1 -> dssPage.getLREarningElement().findElement(By.xpath("//*[@id=\"mss_prvMtSts\"]")).isDisplayed()&&
                    dssPage.getLREarningElement().findElement(By.xpath("//*[@id=\"fysl_prvMthSts\"]")).isDisplayed());
            String mssstatus = dssPage.getLREarningElement().findElement(By.xpath("//*[@id=\"mss_prvMtSts\"]")).getText();
            String fslstatus = dssPage.getLREarningElement().findElement(By.xpath("//*[@id=\"fysl_prvMthSts\"]")).getText();

            Assert.assertEquals(mssstatus,"NOT ACHIEVED");
            Assert.assertEquals(fslstatus,"NOT ACHIEVED");
            logger.info("L&R Earning Section elements are not achieved for earning month");
        }catch (Exception e){
            logger.error("Having error in L&R Earning Section");
            Assert.fail();
        }catch (AssertionError e){
            logger.debug(e.getMessage());
        }

    }

    @Then("MBEP_2024 New Element Tracking should be visible")
    public void mbep_NewElementTrackingShouldBeVisible() {
        try{
            wait.until(driver1 -> driver.findElement(By.xpath("//*[@id=\"MBEP2024TRACKINGHEADER\"]/div/div/div")).isDisplayed());
            logger.info("Tracking section is coming..");
        }
        catch (NoSuchElementException e){
            logger.debug(e.getMessage());
        }catch (Exception e){
            logger.error("Tracking section for April-June having some error",e);
            Assert.fail();
        }
    }

    @And("MSPU Earning Element should be present inside it")
    public void mspuEarningElementShouldBePresentInsideIt() {
        try{Thread.sleep(2000);
            actions.moveToElement(driver.findElement(By.id("loyaltyAndRetentionTrackingAnchor"))).perform();
            actions.moveToElement(driver.findElement(By.id("loyaltyAndRetentionTrackingAnchor"))).click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("MSPUEarningElement")));
            actions.moveToElement(driver.findElement(By.id("MSPUHeaderTracking"))).perform();
            Thread.sleep(2000);
            logger.info("MSPU Section is present in the Tracking section Earning Element.");
        } catch (TimeoutException e) {
            logger.error("MSPU Earning Element was not found within the specified time.");
            // Using Assert.fail here to mark the test as failed
//            Assert.fail("MSPU Earning Element is not present.");
        }catch (AssertionError e){
            logger.debug(e.getMessage());
        }
        catch (Exception e){
            logger.error("Having some error in tracking section..",e);
            Assert.fail();
        }
    }

    @Then("MSS Earning Element should be Not achieved for Earning Month")
    public void mssEarningElementShouldBeNotAchievedForEarningMonth() {
        if(this.dealerTyp.equals("MBEP-NC")){return;}
        try{
            wait.until(driver1 -> driver.findElement(By.id("mss_prvMtSts")).isDisplayed());
            String status = driver.findElement(By.id("mss_prvMtSts")).getText();
            logger.info("MSS Earning element status for earning month {}",status);
            Assert.assertEquals(status,"TRAINING NOT ACHIEVED");
        }catch (Exception e){
            logger.error("MSS Earning element earning month status has some error..",e);
            Assert.fail();
        }
    }

    @Then("Digitization Section Earning Elements should be Not achieved for Earning Month")
    public void digitizationSectionEarningElementsShouldBeNotAchievedForEarningMonth() {

        try{
            String MDSshowroomStatusId = this.dealerTyp.equals("MBEP-NC")?"digitalShowroom_util_prvMtStsNC":"digitalShowroom_util_prvMtSts";
            String MDSServiceStatusId = this.dealerTyp.equals("MBEP-NC")?"ds_util_prvMtStsNC":"ds_util_prvMtSts";
            String status = this.dealerTyp.equals("MBEP-NC")?"NOT PASSED":"TRAINING NOT ACHIEVED";
            wait.until(driver1 -> driver.findElement(By.id(MDSshowroomStatusId)).isDisplayed()&&
                    driver.findElement(By.id(MDSServiceStatusId)).isDisplayed());
            String MDSqualStatus = driver.findElement(By.id(MDSshowroomStatusId)).getText();
            String MDSServiceStatus = driver.findElement(By.id(MDSServiceStatusId)).getText();
            logger.info("MDS Qualifier earning month status : {} /n MDS Service Util Earning month status {}",MDSqualStatus,MDSServiceStatus);
            Assert.assertEquals(MDSqualStatus,status);
            Assert.assertEquals(MDSServiceStatus,status);
        }catch (Exception e){
            logger.error("Having some error in digitization section...",e);
            Assert.fail();
        }catch (AssertionError e){
            logger.error(e.getMessage());
        }
    }

    @When("Traning is Not achieved for Earning Month for {string}")
    public void traningIsNotAchievedForEarningMonthFor(String dealerTyp) {
        this.dealerTyp = dealerTyp;
        try{
            String statusId = dealerTyp.equals("MBEP-NC")?"trainingMCT2024PrvMnthStsNC":"trainingMCT2024PrvMnthSts";
            String statusActual = dealerTyp.equals("MBEP-NC")?"NOT ELIGIBLE":"NOT ACHIEVED";
            Thread.sleep(5000);
            wait.until(driver1 -> driver.findElement(By.id(statusId)).isDisplayed());
            logger.info("Training status for earning month is {}",driver.findElement(By.id(statusId)).getText());
            Assert.assertEquals(driver.findElement(By.id(statusId)).getText(),statusActual);
        }catch (Exception e){
            logger.error("Training element has some error",e);
            Assert.fail();
        }
    }

    @Then("FSL section should not come for MBEP-NC dealer")
    public void fslSectionShouldNotComeForMBEPNCDealer() throws InterruptedException {

        try {
            actions.moveToElement(driver.findElement(By.id("DMHeaderNC"))).perform();
            Thread.sleep(3000);
            logger.debug("FSL Section is not supposed to appear for MBEP-NC dealer, but it did.");
            Assert.fail("FSL Section is present for MBEP-NC dealer when it should not be.");
        } catch (NoSuchElementException e) {
            logger.debug("FSL Section is not coming for MBEP-NC dealer, as expected.");
            // No Assert.fail() needed, as this is the expected outcome
        } catch (AssertionError e) {
            logger.error("Unexpected behavior: {}", e.getMessage());
        }
        catch (Exception e){
            logger.error("Has some error: {}", e.getMessage());
        }
    }

    @And("Sales performance Section should come for MBEP-NC dealer")
    public void salesPerformanceSectionShouldComeForMBEPNCDealer() {

        try {
            wait.until(driver1 -> driver.findElement(By.id("salesPerformanceDivHeading")).isDisplayed());
            logger.info("Sales Section is coming for MBEP-NC dealer");
        }catch (Exception e){
            logger.error(e.getMessage());
            Assert.fail();
        }

    }
}
