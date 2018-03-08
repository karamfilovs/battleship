import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BattleShipPage {
    private WebDriver driver;
    private static final int SLEEP_TIME = 0;
    private static final String PAGE_URL = "http://www.techhuddle.com/tests/battleships/v4test/index.php";
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleShipPage.class);

    @FindBy(how = How.NAME, using = "coord")
    private WebElement coordinatesField;

    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.TAG_NAME, using = "pre")
    private WebElement table;

    By coordinatesLocator = By.xpath("//input[@type='submit']");
    By tableLocator = By.tagName("pre");

    public BattleShipPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterCoordinate(String coordinate) {
        if (isElementPresent(coordinatesLocator)) {
            coordinatesField.clear();
            LOGGER.info("Enter coordinate:" + coordinate);
            coordinatesField.sendKeys(coordinate);
            sleep(SLEEP_TIME);
        }
    }

    public void clickSubmitButton() {
        LOGGER.info("Clicking SUBMIT button:");
        submitButton.click();
        sleep(SLEEP_TIME);
    }

    public void gotoBattleShipPage() {
        LOGGER.info("Navigate to page:" + PAGE_URL);
        driver.navigate().to(PAGE_URL);
    }

    private void sleep(int seconds) {
        try {
            LOGGER.info("Sleeping for:" + seconds + " seconds");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getTableText() {
        if(isElementPresent(tableLocator)){
            LOGGER.info("Text found in table is:" + table.getText());
            return table.getText();
        } else {
            return "";
        }


    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException exception) {
            LOGGER.error(exception.getLocalizedMessage());
            return false;
        }
    }

    public void verifyMessage() {
        String tableText = getTableText();
        int previousHitCount = Counter.hitCount;
        int previousMissCount = Counter.missCount;
        LOGGER.info("Previous HIT Count:" + previousHitCount);
        LOGGER.info("Previous MISS Count:" + previousMissCount);
        Counter.countAll(tableText);
        if (Counter.missCount > previousMissCount) {
            Assertions.assertThat(tableText).as("MISS").containsIgnoringCase("Miss");
        } else {
            Assertions.assertThat(true).as("HIT/SUNK").isEqualTo(tableText.contains("Hit") || tableText.contains("Sunk"));
        }
        if (previousHitCount == Counter.hitCount && previousMissCount == Counter.missCount) {
            try {
                throw new Exception("None of the counters was changed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
