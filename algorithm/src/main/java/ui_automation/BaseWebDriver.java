package ui_automation;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class BaseWebDriver {

	private static BaseWebDriver baseFactory = null;
	
	private BaseWebDriver() {
		
	}
	
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private  WebDriver _webDriver;
	private ThreadLocal<String> browser = new ThreadLocal<String>();
	
	
	
	public static BaseWebDriver getInstance(){
		if(baseFactory == null) {
			
			baseFactory = new BaseWebDriver();
		}
		return baseFactory;
	}
	
	public void setWebDriver(String browser, String enviornment, String platform, Map<String, Object> opt) {
		DesiredCapabilities caps = null;
		
		switch(browser) {
			case "chrome":
			{
//				caps = DesiredCapabilities.chrome();
//		
//		        ChromeOptions chOptions = new ChromeOptions();
//		        Map<String, Object> chromePrefs = new HashMap<String, Object>();
//		
////		        chromePrefs.put("credentials_enable_service",
////		                        false);
////		        chOptions.setExperimentalOption("prefs",
////		                                        chromePrefs);
////		        chOptions.addArguments("--disable-plugins", 
////		                               "--disable-extensions",
////		                               "--disable-popup-blocking");
//		        chOptions.addArguments("start-maximized");
//		        chOptions.addArguments("--disable-notifications");
//		
//		        caps.setCapability(ChromeOptions.CAPABILITY,
//		                           chOptions);
//		        caps.setCapability("applicationCacheEnabled", false);
//		
//		        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//				System.setProperty("webdriver.chrome.driver", Paths.get(getChromeDriverPath().toString()).toString());
//		        //webDriver.set(new ChromeDriver(caps));
//				
//				_webDriver = new ChromeDriver(chOptions);
//				_webDriver.manage().window().maximize();
//				_webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		        // Selenium 3.7.x
		        // webDriver.set(new ChromeDriver(chOptions.merge(caps)));
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("--disable-notifications");

				options.addArguments("window-size=1920,1080");
				System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
				System.setProperty("webdriver.chrome.driver", Paths.get(getChromeDriverPath().toString()).toString());
				System.setProperty("webdriver.chrome.driver", new File(System.getProperty("user.dir")).getParent() + "/algorithm/drivers/mac/chromedriver");

				_webDriver = new ChromeDriver();
				_webDriver.manage().window().maximize();
				_webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		        break;
			}
		}
	}
	
	public  WebDriver getDriver() {
        return webDriver.get();
    }
	
	private String getChromeDriverPath() {
		String path;
		if (System.getProperty("os.name").toLowerCase().contains("mac"))
			path = "./drivers/mac/chromedriver";
		else
			path = "./drivers/linux/chromedriver";
		return path;
	}
}
