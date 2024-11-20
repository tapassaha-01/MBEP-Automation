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
public class DealerEarningStatement extends commonEle {
    WebDriver driver;

    public DealerEarningStatement(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[6]/a")
    WebElement statementMenu;

    @FindBy(id = "addTrackingInBodyTittle")
    WebElement pageHeading;

    @FindBy(id = "searchDealerCode")
    WebElement searchDealer;

    @FindBy(id = "frFinancialMonth")
    WebElement startMonDropDown;

    @FindBy(id = "toFinancialMonth")
    WebElement endMonDropDown;

    @FindBy(id = "submit")
    WebElement submitBtn;

    @FindBy(xpath = "//*[@id=\"area_code_wrapper\"]/div[2]/div[1]/div/table/thead/tr/th")
    List<WebElement> tableHeadings;
}

