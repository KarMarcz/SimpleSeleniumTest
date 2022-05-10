package pl.test.ui.pageObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.test.ui.utils.CustomWaits;

public class CookiesPage {

    private WebDriver driver;
    private Logger logger;
    private CustomWaits waits;

    @FindBy(css = "div .wscrOk")
    private WebElement btnAcceptAllCookies;

    public CookiesPage(WebDriver driver) {
        this.driver = driver;
        waits =  new CustomWaits();
        this.logger = LogManager.getLogger(this);
        PageFactory.initElements(driver, this);
    }

    public void clickAcceptAllCookies(){
        btnAcceptAllCookies.click();
        waits.explicitWaitForElementToBeInvisible(driver, btnAcceptAllCookies);
    }
}
