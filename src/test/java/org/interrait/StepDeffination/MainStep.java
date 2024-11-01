//package org.interrait.StepDeffination;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.interrait.PageObjects.LoginPage;
//import org.interrait.PageObjects.PageObjectManager;
//import org.interrait.utils.testContextSetup;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//
//import java.io.IOException;
//import java.time.Duration;
//
//public class MainStep extends testContextSetup {
//
//    testContextSetup contextSetup ;
//    PageObjectManager objectmanager;
////    WebDriver driver ;
////    WebDriverWait wait ;
////    LoginPage login;
////    LandingPage landingPage;
////    PaymentMangementPage paymentMangementPage;
//    public MainStep(testContextSetup testContextSetup) throws IOException {
//            this.contextSetup = testContextSetup;
//        objectmanager = te
//    }
//
//
//
//    @Given("User is on Landing Page {string}")
//    public void userIsOnLandingPage(String url) {
//
////        driver.get(url);
//        login.goTo(url);
//
//        driver.manage().window().maximize();
//        wait = new WebDriverWait(this.driver, Duration.ofSeconds(150));
////        wait.until(ExpectedConditions.titleContains("MBEP 2023 Dealer Status Summary"));
//    }
//
//    @When("User login into application with {string} and {string}")
//    public void userLoginIntoApplicationWithAnd(String username, String password) throws InterruptedException {
//        landingPage = login.loginApplicaiton(username,password);
////       Thread.sleep(2000)
//    }
//    @Then("Nevigate to the Payment Management Page")
//    public void nevigateToThePaymentManagementPage() {
//
//        paymentMangementPage = landingPage.goToPaymentManagement();
//
//    }
//
//    @And("select dlrcd {string}, mon {string}, year {string}, processTyp {string} and submit")
//    public void selectDlrcdMonYearProcessTypAndSubmit(String dlrcd, String mon, String year, String processTyp) throws InterruptedException {
//        paymentMangementPage.searchDealer(dlrcd,mon,year,processTyp);
//        paymentMangementPage.submitsearchbtn.click();
//        Thread.sleep(2000);
//    }
//
//    @And("Set Base_Qal as {string}, Training as {string}, L&R as {string}, Digitization as {string}")
//    public void setBase_QalAsTrainingAsLRAsDigitizationAs(String arg0, String arg1, String arg2, String arg3) throws InterruptedException {
//
//        Thread.sleep(2000);
//        paymentMangementPage.getBasequalifierstatus().click();
//        //selecting top checkbox to make all the bnus status "Y"
//        paymentMangementPage.getBasequalelecheckbox().click();
//        //submit the changes in base-qualifier
//        paymentMangementPage.getSubmitbtndigpopup().click();
//        //making training as pass
//        paymentMangementPage.changeTrainFlag("Y");
//        //making L&R section pass
//        paymentMangementPage.makeLRPass();
//        //make dig section pss
//        paymentMangementPage.makeDigPass();
//
//    }
//
//    @And("Adding comment {string}")
//    public void addingComment(String comment) {
//        paymentMangementPage.setCommentEleBox(comment);
//
//    }
//
//    @Then("click on the Add button")
//    public void clickOnTheAddButton() {
//       paymentMangementPage.addBtnClick();
//        String alertText="";
//        try {
//            // Wait for the alert to be present
////            WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout
//            wait.until(ExpectedConditions.alertIsPresent());
//
//            // Switch to the alert and get the text
//            Alert alert = driver.switchTo().alert();
//            alertText = alert.getText();
//
//            // Accept the alert
//            alert.accept();
//
//            // Print the alert text (for verification purposes)
//            System.out.println("Alert text: " + alertText);
//
//            // Additional actions if needed after handling the alert
//            // Thread.sleep can be avoided by using proper wait conditions
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            e.printStackTrace();}
//
//        Assert.assertEquals(alertText, "Payment record successfully added");
//
//    }
//
//
//
//}
