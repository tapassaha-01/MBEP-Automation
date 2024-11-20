package org.interrait.PageObjects;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
@Setter
public class EarningDetails extends commonEle{
        WebDriver driver;
        public EarningDetails(WebDriver driver){
            super(driver);
            this.driver = driver;
            PageFactory.initElements(driver,this);
        }
    @FindBy(id = "addTrackingInBodyTittle")
    WebElement earningDetailsHeader;

@FindBy(xpath = "//*[@id=\"returnBtn\"]/input")
    WebElement returnBtn;

@FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[4]/a")
    WebElement earningDetailsMenu;
@FindBy(xpath = "//*[@id=\"area_code\"]/thead/tr/th")
    List<WebElement> tableMonHeader;

}
