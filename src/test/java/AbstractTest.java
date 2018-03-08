import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AbstractTest {
    public static WebDriver driver = null;


    public void startBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        //To maximize browser
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //Implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest(ITestResult result) {
        screenShot(result);
        driver.quit();
    }

    protected void screenShot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File src = screenshot.getScreenshotAs(OutputType.FILE); //capture screenshot
                String fileName = result.getName() + ".png";
                FileUtils.copyFile(src, new File("E:\\\\screenshots\\\\" + fileName)); //copy file to location
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }

        }
    }

}


