package definitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BattleShipPage;
import pages.Counter;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BattleShipStepDefinitions {
    //Browser settings
    private static final String BROWSER = "chrome";
    public static final Logger LOGGER = LoggerFactory.getLogger(BattleShipStepDefinitions.class);

    private BattleShipPage battleShipPage;
    private WebDriver driver;
    private Counter counter;


    private void startBrowser(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            configureBrowser();
        }
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            configureBrowser();
        }

    }

    private void configureBrowser() {
        driver.manage().deleteAllCookies(); //delete cookies
        driver.manage().window().maximize(); //To maximize browser
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);   //Implicit wait
    }


    @Before
    public void before() {
        startBrowser(BROWSER);
    }


    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File src = screenshot.getScreenshotAs(OutputType.FILE); //capture screenshot
                String fileName = scenario.getName() + ".png";
                FileUtils.copyFile(src, new File("target\\screenshots\\" + fileName)); //Image is stored here if test fails
                LOGGER.debug("Successfully captured a screenshot");
                LOGGER.debug("Stored image:" + fileName + " at:" + "E:\\screenshots\\");
            } catch (Exception e) {
                LOGGER.error("Exception while taking screenshot " + e.getMessage());
            }
        }
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

    @When("^I hit multiple coordinates:$")
    public void enterUsername(List<String> coordinates) {
        coordinates.forEach(coordinate -> battleShipPage.hitCoordinate(coordinate));
    }

    @And("^I press Submit button$")
    public void iPressSubmitButton() {
        battleShipPage.clickSubmitButton();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Then("^message should be correct$")
    public void itemSuccessMessage() {
        battleShipPage.verifyMessage();
    }

    @Then("^ships coordinates count should be:(\\d+)$")
    public void shipsCountShouldBe(int count) {
        counter = new Counter(battleShipPage.getTableText());
        Assertions.assertThat(counter.getHitCount()).as("HIT COUNT").isEqualTo(count);
    }

    @Then("^miss count should be:(\\d+)$")
    public void missCountShouldBe(int count) {
        counter = new Counter(battleShipPage.getTableText());
        Assertions.assertThat(counter.getMissCount()).as("MISS COUNT").isEqualTo(count);
    }


    @And("^button label should be \"(.*)\"")
    public void buttonLabelShouldContain(String label) {
        Assertions.assertThat(battleShipPage.getSubmitButtonValue()).as("Submit Button").contains(label);
    }

    @And("^enter form should contain text \"(.*)\"")
    public void formShouldContain(String text) {
        Assertions.assertThat(battleShipPage.getFormText()).as("Submit Button").contains(text);
    }

    @Then("^table should contain text \"([^\"]*)\"$")
    public void tableShouldContainText(String text) throws Throwable {
        Assertions.assertThat(battleShipPage.getTableText()).as("Table Information").contains(text);
    }

    @And("^page url is equal to \"([^\"]*)\"$")
    public void pageUrlIsEqualTo(String url) {
        LOGGER.info("This is my demo step");
    }
}
