package pl.test.ui.pageObject;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.test.ui.utils.CustomWaits;

import java.util.List;


public class GetHelpPage {

    private WebDriver driver;
    private Logger logger;
    private CustomWaits customWaits;

    @FindBy(className = "faq-card")
    List<WebElement> fraQuestionTopics;

    public GetHelpPage(WebDriver driver) {
        this.driver = driver;
        customWaits =  new CustomWaits();
        logger = LogManager.getLogger(this);
        PageFactory.initElements(driver, this);
    }

    private WebElement getSpecificFrameFromFaqList(String headerName){

        for(WebElement element : fraQuestionTopics){
            if(element.findElement(By.xpath("./div/h2")).getText().equals(headerName)){
                return element;
            }
        }
        return null;
    }

    private WebElement getSpecificLinkFromFrameContainsString(String headerName, String question){
        WebElement fraByHeader = getSpecificFrameFromFaqList(headerName);
        for (WebElement element : fraByHeader.findElements(By.xpath(".//a"))){
            if(element.getText().contains(question)){
                return element;
            }
        }
        return null;
    }

    private WebElement getSpecificLinkFromFrameByInteger(String headerName, Integer number){
        WebElement fraByHeader = getSpecificFrameFromFaqList(headerName);
        return fraByHeader.findElements(By.xpath(".//a")).get(number);
    }

    public String getHeaderByName(String headerName) {
        try {
            WebElement fraByHeader = getSpecificFrameFromFaqList(headerName);
            logger.debug("The name of the header is: " + fraByHeader.findElement(By.xpath("./div/h2")).getText());
            return fraByHeader.findElement(By.xpath("./div/h2")).getText();
        }catch (NullPointerException e){
            e.getMessage();
        }
        return "No such Header as:" + headerName;
    }

    public Integer getLinksSize(String headerName){
        try {
            WebElement fraByHeader = getSpecificFrameFromFaqList(headerName);
            logger.debug("There are: " + fraByHeader.findElements(By.xpath(".//a")).size() + " links");
            return fraByHeader.findElements(By.xpath(".//a")).size();
        }catch (NullPointerException e){
            e.getMessage();
        }
        return null;
    }

    public String getAttributeValue(String headerName, String question, String attribute){
        WebElement lnkSpecifiedByQuestion = getSpecificLinkFromFrameContainsString(headerName, question);
        logger.debug("URL for this link is : " + lnkSpecifiedByQuestion.getAttribute(attribute));
        return lnkSpecifiedByQuestion.getAttribute(attribute);
    }

    public void clickLinkInFrame(String headerName, Integer number){
        WebElement lnkSpecifiedByNumber = getSpecificLinkFromFrameByInteger(headerName, number);
        customWaits.explicitWaitForElementToBeClickable(driver, lnkSpecifiedByNumber);
        lnkSpecifiedByNumber.click();
    }
}
