package org.interrait.PageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class commonEle {


    @FindBy(id = "searchDealerCode")
    WebElement dealerCode;

    @FindBy(id="financialMonth")
    WebElement monDrop;

    @FindBy(id = "financialYearDSSNew")
    WebElement yearDrop;

    @FindBy(id="processTypeSearch")
    WebElement processType;

    @FindBy(id="submit")
    public WebElement submitsearchbtn;


    public WebDriver driver;
    public Actions actions;
    public Select select;
    public WebDriverWait wait;
    public commonEle(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        this.wait= new WebDriverWait(this.driver, Duration.ofSeconds(150));

    }
    public boolean isAlertPresent(WebDriver driver) {
        try {
            // Try to switch to the alert
            Alert alert = driver.switchTo().alert();
            // If successful, alert is present
            return true;
        } catch (NoAlertPresentException e) {
            // If NoAlertPresentException is thrown, no alert is present
            return false;
        }
    }

    public void searchDealer(String dlrcd, String mon,String Year){
        wait.until(ExpectedConditions.visibilityOf(this.dealerCode));
        this.dealerCode.sendKeys(dlrcd);
        select = new Select(this.monDrop);
        select.selectByValue(mon);
        select = new Select(this.yearDrop);
        select.selectByValue(Year);
    }


}
