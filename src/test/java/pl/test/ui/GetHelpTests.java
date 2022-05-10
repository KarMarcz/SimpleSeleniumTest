package pl.test.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.test.ui.pageObject.CookiesPage;
import pl.test.ui.pageObject.GetHelpPage;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetHelpTests {

    private WebDriver driver;
    private CookiesPage cookiesPage;
    private GetHelpPage getHelpPage;
    private Logger logger = LogManager.getLogger(this);

    @BeforeMethod
    public void setup(){
        //TODO driver into properties
        WebDriverManager.firefoxdriver().setup();

        //TODO driver into properties
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.nordea.fi/en/personal/get-help/");

        cookiesPage = new CookiesPage(driver);
        getHelpPage = new GetHelpPage(driver);
    }

    @Test
    public void headerTest(){
        cookiesPage.clickAcceptAllCookies();
        String urlForLink = getHelpPage.getAttributeValue("Cards", "forgotten my PIN", "href");
        assertThat(getHelpPage.getHeaderByName("Cards")).as("No such Header").isEqualTo("Cards");
        assertThat(getHelpPage.getLinksSize("Cards")).as("Different number of links").isEqualTo(3);
        assertThat(urlForLink).as("Link is null").isNotNull();
        getHelpPage.clickLinkInFrame("Cards", 1);
        assertThat(driver.getCurrentUrl()).as("Diffrent Page than expected").isEqualTo(urlForLink);
    }

    @AfterMethod
    public void clean(){
        driver.quit();
    }
}
