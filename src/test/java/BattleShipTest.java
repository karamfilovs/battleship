import org.fest.assertions.Assertions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

public class BattleShipTest extends AbstractTest {
    private BattleShipPage battleShipPage;

    @BeforeMethod
    public void beforeTest() {
        startBrowser();
        battleShipPage = PageFactory.initElements(driver, BattleShipPage.class);
    }

    @Test
    public void gotoPageEnterCoordinate() {
        battleShipPage.gotoBattleShipPage();
        battleShipPage.enterCoordinate("B5");
        battleShipPage.clickSubmitButton();
        battleShipPage.verifyMessage();
    }

    @Test
    public void hitAllBattleShips() {
        List<String> coordinates = Lists.newArrayList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
        battleShipPage.gotoBattleShipPage();
        coordinates.forEach(letter -> {
                    for (int i = 1; i <= 10; i++) {
                        battleShipPage.hitCoordinateAndCheckMessage(letter + i);
                    }
                }
        );

    }

    @Test
    public void showBattleShips() {
        battleShipPage.gotoBattleShipPage();
        battleShipPage.enterCoordinate("show");
        battleShipPage.clickSubmitButton();
        Assertions.assertThat(battleShipPage.getTableText()).as("Table").contains("x");
        Counter.countAll(battleShipPage.getTableText());
        Assertions.assertThat(Counter.hitCount).as("HIT COUNT").isEqualTo(13);
    }

    @Test
    public void resetGame() {
        battleShipPage.gotoBattleShipPage();
        battleShipPage.enterCoordinate("reset");
        battleShipPage.clickSubmitButton();
        Counter.countAll(battleShipPage.getTableText());
        Assertions.assertThat(Counter.hitCount).as("HIT COUNT").isEqualTo(0);
        Assertions.assertThat(Counter.missCount).as("MISS COUNT").isEqualTo(0);
    }


}
