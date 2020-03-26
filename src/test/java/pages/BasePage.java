package pages;

import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasePage {
    public static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void typeText(WebElement element, String text) {
        Objects.requireNonNull(element, "Element should not be null");
        LOGGER.info("Typing text:" + text);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void clickButton(WebElement element) {
        Objects.requireNonNull(element, "Element should not be null");
        LOGGER.info("Clicking button:" + element.getText());
        element.click();
    }

    public void gotoPage(String siteURL, String page) {
        LOGGER.info("Navigating to " + siteURL + page + " page");
        driver.navigate().to(siteURL + page);
    }

    public String getText(WebElement element) {
        Objects.requireNonNull(element, "Element should not be null");
        return element.getText().trim();
    }

    public boolean isVisible(WebElement element) {
        Objects.requireNonNull(element, "Element should not be null");
        LOGGER.info("Element displayed:" + element.isDisplayed());
        return element.isDisplayed();
    }

    public void clear(WebElement element) {
        Objects.requireNonNull(element, "Element should not be null");
        LOGGER.info("Clearing element:" + element.getText());
        element.clear();
    }


    public String getAttribute(WebElement element, String attribute) {
        Validate.notNull(element, "Element should not be null");
        return element.getAttribute(attribute);
    }

    public List<String> getAllDropdownOptions(Select select) {
        List<String> options = new ArrayList<>();
        select.getOptions().forEach(option -> options.add(option.getText()));
        LOGGER.info("Options found:" + options.toString());
        return options;
    }


}
