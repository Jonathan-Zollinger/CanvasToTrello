import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Tools {
    public static void Click(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    public static WebDriver getHeadlessDriverSpecifyDownloadDirectory(
            String driverPath, String downloadFilepath, String mimeType) {

        if (mimeType == null) {
            mimeType = "application/x-gzip";
        }
        System.setProperty("webdriver.gecko.driver", driverPath);
        FirefoxProfile profile = new FirefoxProfile();

        //set Firefox profile preferences
        /* it seems like you could get away without changing the browser.download.folderList preference to "2", but
        without it we can't change the browser.download.dir preference. */
        profile.setPreference("browser.download.folderList", 2);//uses last download directory as destination directory
        profile.setPreference("browser.download.dir", downloadFilepath); //if path = invalid, goes to downloads folder

    /* the second argument in these preferences is what's called a "mimetype" of a filetype.
    I find the easiest way to figure the proper mimetype for your use is to follow Florent B's suggestion
    and look at the network panel under the developer tools under a manual run through. https://bit.ly/2FEYB0p */
        profile.setPreference("browser.helperApps.neverAsk.openFile", mimeType);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", mimeType);

        //set Firefox options
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(false); //run headless version of firefox

        //include profile in options
        options.setProfile(profile); //include all the profile settings in this option set

        //initiate new instance of firefox
        return new FirefoxDriver(options); //call the driver w/ our specified options (and profile).

    }//end public static WebDriver getHeadlessDriverSpecifyDownloadDirectory(String downloadFilepath, String mimeType)

    public static void closeWalmartOnboardingPrompt(WebDriver driver) {
        //define the selection
        final By onboardBtn = By.className("OnboardingModal__closeBtn___2xhJM");

        waitAndClick(driver, onboardBtn, "onboarding button");

    }//end public static void closeWalmartOnboardingPrompt(WebDriver driver)

    public static void waitAndClick(WebDriver driver, By clicker, String actionName) {
        try {
            //wait for the element to be clickable
            new WebDriverWait(driver, 4)
                    .until(ExpectedConditions
                            .elementToBeClickable(clicker));
            //now click the element
            driver.findElement(clicker).click();
            System.out.printf("driver clicked the %s%n", actionName);
        } catch (Exception e) {
            System.out.printf("driver failed to click the %s%n", actionName);
        }//end try/catch for WebDriverWait->clicker to be clickable
    }//end public static void waitAndClick(WebDriver driver, By clicker, String actionName)

    public static void clickThroughLinkText(WebDriver driver, ArrayList<String> selectors) {
        for (int i = 0; i < selectors.size(); i++) {
        /*because the webpage may respond slower than our program, loop through a
            couple times if the driver action fails */
            int count = 2;
            while (count > 0) {
                try {
                    driver.findElement(
                            By.linkText(selectors.get(i)))
                            .click();
                    System.out.printf("driver clicked \"%s\"%n", selectors.get(i));
                    break;
                } catch (Exception e) {
                    System.out.printf("driver failed to click selector \"%s\", " +
                            "will try %dx more%n", selectors.get(i), count - 1);
                    count--;
                    //wait 1/2 second till looping through
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (Exception d) {
                        System.out.print("sleep failed");
                    }
                }//end catch (Exception e)
            }//end int count = 5; while(count > 0)
        }//end for(int i = 0; i < selectors.size(); i++)
    }//end public static void clickThroughCssArrayList(WebDriver driver, ArrayList<String> list)

    public static void clickThroughByList(WebDriver driver, ArrayList<Array> selectors) {
        for (int i = 0; i < selectors.size(); i++) {

        }//end for(int i = 0; i < selectors.size(); i++)
    }//end public static void clickThroughCssArrayList(WebDriver driver, ArrayList<String> list)
}
