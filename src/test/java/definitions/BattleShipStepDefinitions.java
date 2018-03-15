package definitions;

import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.fest.assertions.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BattleShipPage;
import pages.Counter;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BattleShipStepDefinitions {
    //Browser settings
    public static final Logger LOGGER = LoggerFactory.getLogger(BattleShipStepDefinitions.class);
    private static final String chromeDriverLocation = "C:\\webdrivers\\chromedriver.exe";
    private static final String firefoxDriverLocation = "C:\\webdrivers\\geckodriver.exe";
    private static final String ieDriverLocation = "C:\\webdrivers\\IEDriverServer.exe";
    private static final String chromeProperty = "webdriver.chrome.driver";
    private static final String firefoxProperty = "webdriver.gecko.driver";
    private static final String ieProperty = "webdriver.ie.driver";

    BattleShipPage battleShipPage;
    WebDriver driver;
    Counter counter;


    private void startBrowser(String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty(firefoxProperty, firefoxDriverLocation);
            driver = new FirefoxDriver();
            configureBrowser(browser);
        }
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty(chromeProperty, chromeDriverLocation);
            driver = new ChromeDriver();
            configureBrowser(browser);
        }
        if (browser.equalsIgnoreCase("ie")) {
            System.setProperty(ieProperty, ieDriverLocation);
            driver = new InternetExplorerDriver();
            configureBrowser(browser);
        }

    }

    private void configureBrowser(String browser) {
        LOGGER.info("Starting browser:" + browser);
        driver.manage().deleteAllCookies(); //delete cookies
        driver.manage().window().maximize(); //To maximize browser
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   //Implicit wait
    }


    @Before
    public void before() {
        startBrowser("chrome");
    } //Current browser in use

    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File src = screenshot.getScreenshotAs(OutputType.FILE); //capture screenshot
                String fileName = scenario.getName() + ".png";
                FileUtils.copyFile(src, new File("E:\\screenshots\\" + fileName)); //Image is stored here if test fails
                LOGGER.info("Successfully captured a screenshot");
                LOGGER.info("Stored image:" + fileName + " at:" + "E:\\screenshots\\");
            } catch (Exception e) {
                LOGGER.info("Exception while taking screenshot " + e.getMessage());
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
    }


    @Then("^message should be correct$")
    public void itemSuccessMessage() {
        battleShipPage.verifyMessage();
    }

    @Then("^ships count should be:(\\d+)$")
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
}
