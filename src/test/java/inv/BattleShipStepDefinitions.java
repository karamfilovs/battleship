package inv;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fest.assertions.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.BattleShipPage;
import pages.Counter;

import java.util.concurrent.TimeUnit;

public class BattleShipStepDefinitions {
    BattleShipPage battleShipPage;
    WebDriver driver;


    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
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

    @When("^I enter coordinate:\"(.*)\"$")
    public void enterUsername(String coordinate) {
        battleShipPage.enterCoordinate(coordinate);
    }

    @And("^I press Submit button$")
    public void iPressSubmitButton() {
        battleShipPage.clickSubmitButton();
    }


    @Then("^message should be correct$")
    public void itemSuccessMessage() {
        battleShipPage.verifyMessage();
    }

    @Then("^ships count should be:(\\d+)$")
    public void shipsCountShouldBe(int count) {
        Counter.countAll(battleShipPage.getTableText());
        Assertions.assertThat(Counter.hitCount).as("HIT COUNT").isEqualTo(count);
    }

    @Then("^miss count should be:(\\d+)$")
    public void missCountShouldBe(int count) {
        Counter.countAll(battleShipPage.getTableText());
        Assertions.assertThat(Counter.missCount).as("MISS COUNT").isEqualTo(count);
    }

    @Then("^remain count should be:(\\d+)$")
    public void remainCountShouldBe(int count) {
        Counter.countAll(battleShipPage.getTableText());
        Assertions.assertThat(Counter.remainCount).as("REMAIN COUNT").isEqualTo(count);
    }
}
