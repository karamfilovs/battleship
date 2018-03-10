package inv;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BattleShipPage;

import java.util.concurrent.TimeUnit;

public class BattleShipStepDefinitions {
    BattleShipPage battleShipPage;
    WebDriver driver;


    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        driver.quit();
    }

    @Given("^user is on BattleShips Page$")
    public void user_is_on_Home_Page() {
        battleShipPage = new BattleShipPage(driver);
        battleShipPage.gotoPage();
    }
}
