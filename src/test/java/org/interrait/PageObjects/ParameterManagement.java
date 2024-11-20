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
public class ParameterManagement extends commonEle{
    WebDriver driver;
    public ParameterManagement(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[5]/ul/li[4]/a")
    WebElement parameterManagementScreen;

    @FindBy(xpath = "//*[@id=\"defaultBody\"]/div/div/div[3]/table[3]/tbody/tr")
    List<WebElement> MBEP_MBEP_BrandPayoutElement;
}