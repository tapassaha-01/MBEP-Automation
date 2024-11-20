package org.interrait.PageObjects;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
@Setter
public class EarningSummeryPage extends commonEle{
    WebDriver driver;
    public EarningSummeryPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[3]/a")
    WebElement earningSummeryMenu;

    @FindBy(id = "addTrackingInBodyTittle")
    WebElement earningSummeryHeading;

    @FindBy(xpath = "//*[@id=\"area_code_6\"]/thead/tr/th")
    List<WebElement> summeryTableHeader;

    @FindBy(id = "searchDealerCode")
    WebElement dealerCode;

    @FindBy(id="financialMonth")
    WebElement monDropDown;

    @FindBy(id="financialYear")
    WebElement yearDropDown;

    @FindBy(id = "submit")
    WebElement submitBtn;

}
