package mobilius;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * @author mishadoff
 */
public class AutoRoute {

    private static WebDriver driver;

    // TODO move to file
    private static String[] path =  new String[]{"50.446041,30.512466",            "50.44585,30.514312",            "50.445672,30.515363",            "50.445467,30.516694",            "50.445317,30.517831",            "50.445221,30.518689",            "50.445085,30.519569",            "50.444852,30.520513",            "50.444319,30.521049",            "50.443855,30.520899",            "50.443281,30.520663",            "50.442461,30.520384",            "50.442106,30.520856",            "50.44186,30.521843",            "50.441108,30.522251",            "50.440384,30.522466",            "50.439728,30.523088",            "50.439126,30.523903",            "50.438648,30.524933",            "50.438788,30.525325",            "50.439085,30.525481",            "50.43926,30.525717",            "50.439417,30.525958",            "50.439885,30.526634",            "50.44023,30.527127",            "50.440698,30.527766",            "50.441354,30.528603",            "50.441887,30.529418",            "50.442461,30.530169",            "50.443062,30.530984",            "50.443048,30.532229",            "50.442652,30.532765",            "50.442748,30.533645",            "50.443199,30.534139",            "50.443773,30.534911",            "50.444497,30.535834",            "50.444675,30.536478",            "50.444511,30.537143"};
    public static void main(String[] args) throws Exception{
        // init driver
        System.out.println("[INFO] Init Driver");

        driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8989";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // TODO ALERT: Building the route
//        executeJS("alert('Building the route');");
//       waitForAlert();

        System.out.println("[INFO] Building the route");

        // auto route
        driver.get(baseUrl + "/");
        driver.findElement(By.id("fromInput")).clear();
        driver.findElement(By.id("fromInput")).sendKeys("50.446355,30.510578");
        driver.findElement(By.id("toInput")).clear();
        driver.findElement(By.id("toInput")).sendKeys("50.444511,30.537143");
        driver.findElement(By.id("searchButton")).click();
        waitToLoad();

        // TODO ALERT: Start moving!
        //executeJS("alert('Start moving');");
        //waitForAlert();

        System.out.println("[INFO] Start moving");

        for (String pathElement : path) {
            driver.findElement(By.id("fromInput")).clear();
            driver.findElement(By.id("fromInput")).sendKeys(pathElement);
            driver.findElement(By.id("searchButton")).click();
            waitToLoad();
        }

        System.out.println("[INFO] Finish!");
        //driver.quit();
    }

    private static void waitToLoad() throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60) throw new RuntimeException("Timeout: INIT");
            try { if (isElementPresent(By.id("instructions"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }

    private static boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void waitForAlert() throws Exception{
        Thread.sleep(1000);
        for (int second = 0;; second++) {
            if (second >= 60) throw new RuntimeException("Timeout: INIT");
            boolean alert = true;
            try {
                driver.switchTo().alert();
                alert = true;
            } catch (NoAlertPresentException Ex) {
                alert = false;
            }
            if (!alert) {
                Thread.sleep(1000);
                break;
            }
            Thread.sleep(1000);
        }
    }

    public static void executeJS(String s) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(s);
        }
    }
}
