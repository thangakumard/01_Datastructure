package ui_automation;

/***
 * Reference : https://subscription.packtpub.com/book/web_development/9781788473576/1/ch01lvl1sec11/the-singleton-driver-class
 */

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class CreateDriver {

	// constructor
    private CreateDriver() {
    }
    
    // local variables
    private static CreateDriver instance = null;
    private String browserHandle = null;
    private static final int IMPLICIT_TIMEOUT = 0;

    private ThreadLocal<WebDriver> webDriver =
            new ThreadLocal<WebDriver>();

    private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver =
            new ThreadLocal<AppiumDriver<MobileElement>>();

    private ThreadLocal<String> sessionId =
            new ThreadLocal<String>();

    private ThreadLocal<String> sessionBrowser =
            new ThreadLocal<String>();

    private ThreadLocal<String> sessionPlatform =
            new ThreadLocal<String>();

    private ThreadLocal<String> sessionVersion =
            new ThreadLocal<String>();

    private String getEnv = null;

    /**
     * getInstance method to retrieve active driver instance
     *
     * @return CreateDriver
     */
    public static CreateDriver getInstance() {
        if ( instance == null ) {
            instance = new CreateDriver();
        }

        return instance;
    }
    
    /**
     * setDriver method
     *
     * @param browser
     * @param environment
     * @param platform
     * @param optPreferences
     * @throws Exception
     */
    @SafeVarargs
    public final void setDriver(String browser,
                                String environment,
                                String platform,
                                Map<String, Object>... optPreferences)
                                throws Exception {

        DesiredCapabilities caps = null;
        String localHub = "http://127.0.0.1:4723/wd/hub";
        String getPlatform = null;

        switch (browser) {
            case "firefox":
                caps = DesiredCapabilities.firefox();

        FirefoxOptions ffOpts = new FirefoxOptions();
        FirefoxProfile ffProfile = new FirefoxProfile();
                ffProfile.setPreference("browser.autofocus",
                                        true);

                caps.setCapability(FirefoxDriver.PROFILE,
                                   ffProfile);
                caps.setCapability("marionette",
                                   true);

                //webDriver.set(new FirefoxDriver(caps));

                // Selenium 3.7.x
                //webDriver.set(new FirefoxDriver(ffOpts.setProfile(caps)));

                break;
            case "chrome":
                caps = DesiredCapabilities.chrome();
                webDriver.set(new ChromeDriver(caps));

                break;
            case "internet explorer":
                caps = DesiredCapabilities.internetExplorer();
                webDriver.set(new 
                              InternetExplorerDriver(caps));

                break;
            case "safari":
                caps = DesiredCapabilities.safari();
                webDriver.set(new SafariDriver(caps));

                break;
            case "microsoftedge":
                caps = DesiredCapabilities.edge();
                webDriver.set(new EdgeDriver(caps));

                break;
            case "iphone":
            case "ipad":
                if (browser.equalsIgnoreCase("ipad")) {
                    caps = DesiredCapabilities.ipad();
                }

                else {
                    caps = DesiredCapabilities.iphone();
                }

//                mobileDriver.set(new IOSDriver<MobileElement>(
//                                 new URL(localHub), caps));

                break;
            case "android":
                caps = DesiredCapabilities.android();
//                mobileDriver.set(new 
//                                 AndroidDriver<MobileElement>(
//                                 new URL(localHub), caps));

                break;
        }
    }

    /** 
     * overloaded setDriver method to switch driver to specific WebDriver
     * if running concurrent drivers
     *
     * @param driver WebDriver instance to switch to
     */
    public void setDriver(WebDriver driver) {
        webDriver.set(driver);

        sessionId.set(((RemoteWebDriver) webDriver.get())
        .getSessionId().toString());

        sessionBrowser.set(((RemoteWebDriver) webDriver.get())
        .getCapabilities().getBrowserName());

        sessionPlatform.set(((RemoteWebDriver) webDriver.get())
        .getCapabilities().getPlatform().toString());

        setBrowserHandle(getDriver().getWindowHandle());
    }

    /**
     * overloaded setDriver method to switch driver to specific AppiumDriver
     * if running concurrent drivers
     *
     * @param driver AppiumDriver instance to switch to
     */
    public void setDriver(AppiumDriver<MobileElement> driver) {
        mobileDriver.set(driver);

        sessionId.set(mobileDriver.get()
        .getSessionId().toString());

        sessionBrowser.set(mobileDriver.get()
        .getCapabilities().getBrowserName());

        sessionPlatform.set(mobileDriver.get()
        .getCapabilities().getPlatform().toString());
    }
    
    /**
     * getDriver method will retrieve the active WebDriver
     *
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return webDriver.get();
    }

    /**
     * getDriver method will retrieve the active AppiumDriver
     *
     * @param mobile boolean parameter
     * @return AppiumDriver
     */
    public AppiumDriver<MobileElement> getDriver(boolean mobile) {
        return mobileDriver.get();
    }

    public String getSessionBrowser() {
    	return this.sessionBrowser.get();
    }
    /**
     * getCurrentDriver method will retrieve the active WebDriver
     * or AppiumDriver
     *
     * @return WebDriver
     */
    public WebDriver getCurrentDriver() {
        if ( getInstance().getSessionBrowser().contains("iphone") ||
             getInstance().getSessionBrowser().contains("ipad") ||
             getInstance().getSessionBrowser().contains("android") ) {

            return getInstance().getDriver(true);
        }

        else {
            return getInstance().getDriver();
        }
    }
    
    /**
     * driverWait method pauses the driver in seconds
     *
     * @param seconds to pause
     */
    public void driverWait(long seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        }

        catch (InterruptedException e) {
            // do something
        }
    }

    /**
     * driverRefresh method reloads the current browser page
     */
    public void driverRefresh() {
        getCurrentDriver().navigate().refresh();
    }
    
    /**
     * closeDriver method quits the current active driver
     */
    public void closeDriver() {
        try {
            getCurrentDriver().quit();
        }

        catch ( Exception e ) {
            // do something
        }
    }
    
    public void setBrowserHandle(String window) {
    	getCurrentDriver().switchTo().window(window);
    }
}
