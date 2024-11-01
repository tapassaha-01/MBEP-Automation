package org.interrait.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class PaymentMangementPage extends commonEle {
    WebDriver driver;
    public PaymentMangementPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "searchDealerCode")
    WebElement dealerCode;

    @FindBy(id="financialMonth")
    WebElement monDrop;

    @FindBy(id = "financialYearPMNew")
    WebElement yearDrop;

    @FindBy(id="processTypeSearch")
    WebElement processType;

    @FindBy(id="submit")
    public WebElement submitsearchbtn;

    @FindBy(xpath = "//*[@id=\"TrainingQualification\"]/a")
    WebElement trainingQual;

    @FindBy(id = "TrngBQ_0")
    WebElement trainingCheckBox;

    @FindBy(id = "DigShrmQualPayPercentageStatus")
    WebElement digshrmqualstatuspaypercent;

    @FindBy(id = "DigSrvcUtilPayPercentageStatus")
    WebElement digsrcutilpaypercentstatus;

    @FindBy(id="TrngBQ_0QualificationStatusBonus")
    WebElement trainingqualstatusflag;

    @FindBy(xpath = "//div[contains(@class,\"contactText\")]/div[4]//input")
    WebElement submitbtntraiingpopup;

    @FindBy(id = "MSSPayPercentageStatus")
    WebElement msspaypercentstatus;

    @FindBy(xpath = "//*[@id=\"DigQualification\"]/a")
    WebElement digqualpopup;

    @FindBy(id = "DigBQ_0")
    WebElement cvcheckbox;

    @FindBy(id = "DigBQ_0QualificationStatusBonus")
    WebElement cvstatusflag;

    @FindBy(id = "DigShrmEnrlPayPercentageStatus")
    WebElement digshrmenrlpaypercentstatus;

    @FindBy(id = "buttonPopupSave")
    WebElement popupsubmitbtn;

    @FindBy(id="BQQualification")
    WebElement basequalifierstatus;

    @FindBy(id = "LRQualification")
    WebElement lrqualstatus;

    @FindBy(id = "BQBQHeaderCheckbox")
    WebElement basequalelecheckbox;

    @FindBy(id = "LRBQHeaderCheckbox")
    WebElement lrbqheadercheckbox;

    @FindBy(id = "LREEHeaderCheckbox")
    WebElement lreeheadercheckbox;

    @FindBy(id = "DigBQHeaderCheckbox")
    WebElement digbqheadercheckbox;

    @FindBy(id = "DigEE_0")
    WebElement digenrlcheckbox;

    @FindBy(id = "DigEE_1")
    WebElement digqualcheckbox;

    @FindBy(id = "DigEE_2")
    WebElement digsrvcheckbox;

    @FindBy(id = "DigEE_3")
    WebElement digsrvutilcheckbox;

    @FindBy(id = "addCommentText")
    WebElement commentEleBox;

    @FindBy(id = "add")
    WebElement addbtn;

    public void addBtnClick(){
        this.addbtn.click();
    }

    public void setCommentEleBox(String comment) {
        commentEleBox.sendKeys(comment);
    }

    public void makeDigPass(){
        this.digqualpopup.click();
        this.digbqheadercheckbox.click();//making base qual of dig "Y"
        this.digenrlcheckbox.click();
        this.digqualcheckbox.click();
        this.digsrvcheckbox.click();
        this.digsrvutilcheckbox.click();
        this.popupsubmitbtn.click();
    }

    public void makeLRPass(){
        this.lrqualstatus.findElement(By.tagName("a")).click();
        this.lrbqheadercheckbox.click();//making base qual all "Y"
        this.lreeheadercheckbox.click();// making all ee "Y"
        this.popupsubmitbtn.click();//submit changes
    }



    public WebElement getBasequalelecheckbox() {
        return basequalelecheckbox;
    }

    public WebElement getBasequalifierstatus() {
        return basequalifierstatus.findElement(By.tagName("a"));
    }

    public WebElement getDigshrmenrlpaypercentstatus() {
        return digshrmenrlpaypercentstatus;
    }

    public WebElement getSubmitbtndigpopup() {
        return this.popupsubmitbtn;
    }

    public void searchDealer(String dlrcd, String mon,String Year, String processtyp){
        wait.until(ExpectedConditions.visibilityOf(this.dealerCode));
//        this.dealerCode.sendKeys(dlrcd);
//        select = new Select(this.monDrop);
//        select.selectByValue(mon);
//        select = new Select(this.yearDrop);
//        select.selectByValue(Year);
        searchDealer(dlrcd,mon,Year);
        select = new Select(this.processType);
        select.selectByValue(processtyp);

    }

    public void changeTrainFlag(String flagval) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(this.trainingQual));
        this.trainingQual.click();
        this.trainingCheckBox.click();
        Thread.sleep(3000);
        select = new Select(this.trainingqualstatusflag);
        select.selectByValue(flagval);
        this.submitbtntraiingpopup.click();
    }
    public WebElement getDigShrmPayPercentageStatus(){

        wait.until(ExpectedConditions.visibilityOf(this.digshrmqualstatuspaypercent));
        System.out.println("this.digshrmqualstatuspaypercent: "+this.digshrmqualstatuspaypercent.getText());
        return this.digshrmqualstatuspaypercent;
    }
    public WebElement getDigSrvcUtilPayPercentageStatus(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("DigSrvcUtilPayPercentageStatus")));
        System.out.println("this.DigSrvcUtilPayPercentageStatus: "+this.digsrcutilpaypercentstatus.getText());
        return this.digsrcutilpaypercentstatus;
    }
    public WebElement getMssPayPercentageStatus(){
        return this.msspaypercentstatus;
    }
    public WebElement getCvcheckbox() {
        return cvcheckbox;
    }
    public WebElement getCvstatusflag() {
        return cvstatusflag;
    }
    public WebElement getDigqualpopup() {
        wait.until(ExpectedConditions.visibilityOf(this.digqualpopup));
        return digqualpopup;
    }


}
