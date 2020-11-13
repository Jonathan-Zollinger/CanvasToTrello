import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public abstract class Chauffeur implements WebDriver {
    //fields
    String mimeType;
    boolean headless;
    WebDriver driver;


    //constructors
    public Chauffeur(){
        mimeType = "application/x-gzip";
        boolean headless = false;
        setDriverOptions();
    }
    public Chauffeur(String mimeType){
        this.mimeType = mimeType;
        boolean headless = false;
        setDriverOptions();
    }
    public Chauffeur(boolean headless){
        mimeType = "application/x-gzip";
        this.headless = headless;
    }
    public Chauffeur(boolean headless, String mimeType){
        mimeType = "application/x-gzip";
        this.headless = headless;
        setDriverOptions();
    }


    //methods
    private void setDriverOptions(){

        try{
            System.setProperty("webdriver.gecko.driver", Places.WDRIVER.toString());
        }catch(Exception replaceMe/*TODO: figure what exception to use*/) {
            System.setProperty("webdriver.gecko.driver", Places.LDRIVER.toString());
        }
        FirefoxProfile profile = new FirefoxProfile();

        //set Firefox profile preferences
        /* it seems like you could get away without changing the browser.download.folderList preference to "2", but
        without it we can't change the browser.download.dir preference. */
        profile.setPreference("browser.download.folderList", 2);//uses last download directory as destination directory
//        profile.setPreference("browser.download.dir", downloadFilepath); //if path = invalid, goes to downloads folder

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
        driver = new FirefoxDriver(options);
    }
    private void waitAndClick(By clicker, String actionName){
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
    }

}
