import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

public class BattleShipTest extends AbstractTest {
    int sleepTime = 2;

    @Test
    public void gotoPageEnterCoordinate() {
        gotoBattleShipPage();
        enterCoordinate("B5");
        sleep(sleepTime);
        clickSubmitButton();
        sleep(sleepTime);
        getTableText();
    }

    @Test
    public void hitAllBattleShips() {
        List<String> letters = Lists.newArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
        gotoBattleShipPage();
        letters.forEach(letter -> {
                    for (int i = 1; i <= 10; i++) {
                        enterCoordinate(letter + i);
                        clickSubmitButton();
                        //getTableText();
                    }
                }
        );
    }

    @Test
    public void showBattleShips() {
        gotoBattleShipPage();
        enterCoordinate("show");
        sleep(sleepTime);
        clickSubmitButton();
        getTableText();
    }

    @Test
    public void resetGame() {
        gotoBattleShipPage();
        enterCoordinate("reset");
        sleep(sleepTime);
        clickSubmitButton();
        getTableText();
    }


    private void enterCoordinate(String coordinate) {
        WebElement coordinatesField = driver.findElement(By.name("coord"));
        coordinatesField.clear();
        System.out.println("Typing text:" + coordinate);
        coordinatesField.sendKeys(coordinate);
    }

    private void gotoBattleShipPage() {
        String page = "http://www.techhuddle.com/tests/battleships/v4test/index.php";
        driver.manage().window().maximize();
        System.out.println("Navigate to page:" + page);
        driver.navigate().to(page);
    }

    private void clickSubmitButton() {
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        System.out.println("Clicking button:" + submitButton.getTagName());
        submitButton.click();
    }

    private String getTableText() {
        WebElement table = driver.findElement(By.tagName("pre"));
        System.out.println("Text found:" + table.getText());
        return table.getText();
    }


    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
