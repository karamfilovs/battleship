package pages;

import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PageAction;


public class BattleShipPage {
    private PageAction action;
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
        PageFactory.initElements(driver, this);
        action = new PageAction(driver);
    }

    public void enterCoordinate(String coordinate) {
        action.typeText(coordinatesField, coordinate);
    }

    public void clickSubmitButton() {
        beforeSubmitText = getTableText();
        action.clickButton(submitButton);
    }

    public void gotoPage() {
        action.gotoPage(PAGE_URL, "");
    }

    public String getFormText() {
        return action.getText(form);
    }

    public String getTableText() {
        return action.getText(table);
    }

    public String getSubmitButtonValue() {
        return action.getAttribute(submitButton, "name");
    }


    public void hitCoordinate(String coordinate) {
        enterCoordinate(coordinate);
        clickSubmitButton();
        wait(SLEEP_TIME);
        verifyMessage();
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000); //Dummy sleep to observe reply easier
        } catch (InterruptedException e) {
            e.printStackTrace();
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
