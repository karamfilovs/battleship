package pages;

import org.fest.assertions.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BattleShipPage extends BasePage {
    private static final int SLEEP_TIME = 0;
    private static final String PAGE_URL = "http://www.techhuddle.com/tests/battleships/v4test/index.php";
    private static final Logger LOGGER = LoggerFactory.getLogger(BattleShipPage.class);
    private String beforeSubmitText = "";

    @FindBy(how = How.NAME, using = "coord")
    private WebElement coordinatesField;

    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.TAG_NAME, using = "pre")
    private WebElement table;

    @FindBy(how = How.XPATH, using = "//form[@name='input']")
    private WebElement form;

    public BattleShipPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterCoordinate(String coordinate) {
        typeText(coordinatesField, coordinate);
    }

    public void clickSubmitButton() {
        beforeSubmitText = getTableText();
        clickButton(submitButton);
    }

    public void gotoPage() {
        gotoPage(PAGE_URL, "");
    }

    public String getFormText() {
        return getText(form);
    }

    public String getTableText() {
        return getText(table);
    }

    public String getSubmitButtonValue() {
        return getAttribute(submitButton, "name");
    }


    public void hitCoordinate(String coordinate) {
        enterCoordinate(coordinate);
        clickSubmitButton();
        wait(SLEEP_TIME);
        //verifyMessage();
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000); //Dummy sleep to observe reply easier
        } catch (InterruptedException e) {
            LOGGER.error("Something bad happened");
        }
    }

    public void verifyMessage() {
        Counter counter = new Counter(beforeSubmitText);
        int previousHitCount = counter.getHitCount();
        int previousMissCount = counter.getMissCount();
        counter = new Counter(getTableText());
        LOGGER.info("Previous HIT Count:" + previousHitCount);
        LOGGER.info("Previous MISS Count:" + previousMissCount);
        if (counter.getMissCount() > previousMissCount) {
            Assertions.assertThat(getTableText()).as("MISS").containsIgnoringCase("Miss");
        } else {
            Assertions.assertThat(true).as("HIT/SUNK").isEqualTo(getTableText().contains("Hit") || getTableText().contains("Sunk"));
        }
    }
}
