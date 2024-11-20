package org.interrait.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;
    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="username")
    WebElement username;

    @FindBy(id="password")
    WebElement password;

    @FindBy(xpath = "//*[@id=\"loginForm\"]/div[4]/input")
    WebElement submitbtn;


    public void goTo(String Url) {

        driver.get(Url);
    }
    public LandingPage loginApplicaiton(String username,String pass){
//        driver.manage().deleteAllCookies();
        this.username.sendKeys(username);
        this.password.sendKeys(pass);
        this.submitbtn.click();
        return new LandingPage(driver);
    }
}
