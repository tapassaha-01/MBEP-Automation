package org.interrait.PageObjects;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
@Setter
public class DssPage extends commonEle{
    WebDriver driver;
    public DssPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }


    @FindBy(xpath = "//*[@id=\"mcvpTableFirstRow\"]/th")
    List<WebElement> mcvpCurrMon;

    @FindBy(id="okButton")
    WebElement POP_UP_3_btn;

    @FindBy(id = "mcvpPrvsMthSts")
    WebElement mcvpCurrentMonSts;

    @FindBy(id = "mcvpCurrMthSts")
    WebElement mcvpNxtMonSts;

    @FindBy(xpath = "(//*[@id=\"cssmenu\"])/ul/li[5]/a")
    WebElement adminMenu;

    @FindBy(id = "mcvpDescription")
    WebElement MCVPdescription;

    @FindBy(xpath = "(//*[@id=\"cssmenu\"])/ul[1]/li[5]//li[1]/a")
    WebElement paymentmanagement;

    @FindBy(xpath = "//*[@id=\"loyaltyAndRetentionHeader\"]")
    WebElement LRHeaing;

    @FindBy(xpath = "//*[@id=\"mcvp_expandCollapse\"]")
    WebElement expandMCVP;

    @FindBy(id = "mcvpHeader")
    WebElement mcvpHeader;

    @FindBy(xpath = "//*[@id=\"mcvpLeftTable\"]/tbody/tr[1]/td")
    List<WebElement> InstockMinReqMonData;

    @FindBy(xpath = "//*[@id=\"mcvpRightTable\"]/tbody/tr[2]/td")
    List<WebElement> InstockMinReqMonDataActual;

    @FindBy(xpath = "//*[@id=\"mcvpFooterNote\"]/a")
    WebElement MCVPfooterLink;

    @FindBy(id="base_qualifier_loyalityAndRetentionDiv")
    WebElement LRBasequalifer;

    @FindBy(id="earning_elements_loyalityAndRetentionDiv")
    WebElement LREarningElement;

    @FindBy(id = "MSPUObjMinVal")
    WebElement mspuObjVal;

    @FindBy(id = "MSPUDescription")
    WebElement mspuDescrip;

    @FindBy(xpath = "//*[@id=\"tysl_link\"]/a")
    WebElement MSPUlink;

    @FindBy(id = "baseQualifierHeading")
    WebElement baseQualHeading;

    @FindBy(id = "baseQualifierDescription")
    WebElement baseQualDescription;

    @FindBy(id = "baseQualifiersAnchor")
    WebElement baseQualCollapse;

    @FindBy(id = "FACILITYanchor")
    WebElement FSLcollapse;

    @FindBy(id = "facilityHeading")
    WebElement FSLHeading;

    @FindBy(id = "facilityDescription")
    WebElement FSLDescription;

    @FindBy(id = "dataAgreementHeading")
    WebElement dataAgreeHeading;

    @FindBy(id = "dataAgreementDescription")
    WebElement dtaAgreementDescription;

    @FindBy(id = "brandCommitmentDiv")
    WebElement brandSection;

    @FindBy(xpath = "//*[@id=\"dataContainer\"]/div[9]")
    WebElement digitizationSection;

    @FindBy(id = "FACILITYDetails")
    WebElement FSLtable;

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[3]/a")
    WebElement performance_payment_menu;

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[2]/a")
    WebElement MBEP_2016;

    @FindBy(id = "addTrackingInBodyTittle")
    WebElement MBEP_2016_heading;

    @FindBy(xpath = "//*[@id=\"cssmenu\"]/ul/li[3]/ul/li[1]/a")
    WebElement MBEP_2023;

    @FindBy(id = "baseQualifierParentDiv")
    WebElement BaseQualifier;

    public WebElement getCVAPtxt(){
        return this.digitizationSection.findElement(By.id("cvHeader"));
    }

    public WebElement getCX360collapse() {
        return this.digitizationSection.findElement(By.id("RHanchor"));
    }

    public WebElement getCX360txt(){
        return this.digitizationSection.findElement(By.id("cx360RHHeader"));
    }

    public WebElement getMonDrop(){
        return monDrop;
    }

    public PaymentMangementPage goToPaymentManagement(){

        if(isAlertPresent(driver)){
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            // Accept the alert
            alert.accept();
        }
        if(driver.findElement(By.id("customModalContent")).isDisplayed()){
            driver.findElement(By.id("okButton")).click();
        }

        actions = new Actions(driver);
        actions.moveToElement(adminMenu).perform();
        paymentmanagement.click();
        return new PaymentMangementPage(driver);
    }


}
