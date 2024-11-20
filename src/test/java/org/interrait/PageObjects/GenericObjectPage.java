package org.interrait.PageObjects;


import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
@Setter
public class GenericObjectPage extends commonEle{
    WebDriver driver;
    public GenericObjectPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "addTrackingInBodyTittle")
    WebElement heading;

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[5]/ul/li[7]/ul/li[1]/a")
    WebElement genericObjectiveScreen;
}
