package org.interrait.PageObjects;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    LandingPage landingPage;
    LoginPage loginPage;
    PaymentMangementPage paymentMangementPage;
    DssPage dssPage;

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
