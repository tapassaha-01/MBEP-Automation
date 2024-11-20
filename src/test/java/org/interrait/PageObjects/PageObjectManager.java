package org.interrait.PageObjects;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    LandingPage landingPage;
    LoginPage loginPage;
    PaymentMangementPage paymentMangementPage;
    DssPage dssPage;
    EarningSummeryPage earningSummeryPage;
    EarningDetails earningDetails;
    DealerEarningStatement dealerEarningStatement;
    ParameterManagement parameterManagement;
    GenericObjectPage genericObjectPage;

    public GenericObjectPage getGenericObjectPage() {
        genericObjectPage =new GenericObjectPage(driver);
        return genericObjectPage;
    }

    public ParameterManagement getParameterManagement() {
        parameterManagement = new ParameterManagement(driver);
        return parameterManagement;
    }

    public DealerEarningStatement getDealerEarningStatement() {
        dealerEarningStatement = new DealerEarningStatement(driver);
        return dealerEarningStatement;
    }

    public EarningDetails getEarningDetails() {
        earningDetails = new EarningDetails(driver);
        return earningDetails;
    }

    public EarningSummeryPage getEarningSummeryPage() {
        earningSummeryPage = new EarningSummeryPage(driver);
        return earningSummeryPage;
    }

    public DssPage getDssPage() {
        dssPage = new DssPage(driver);
        return dssPage;
    }
    public void setDssPage(DssPage dssPage){
        this.dssPage = dssPage;
    }

    WebDriver driver ;
    //create every pages getter and also the parameterize constructor
    public PageObjectManager(WebDriver driver){
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        loginPage = new LoginPage(driver);
        return loginPage;
    }

    public PaymentMangementPage getPaymentMangementPage() {
        paymentMangementPage = new PaymentMangementPage(driver);
        return paymentMangementPage;
    }

    public LandingPage getLandingPage() {
        landingPage = new LandingPage(driver);
        return landingPage;
    }
}
