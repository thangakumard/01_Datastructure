package ui_automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SampleUIAutomation {

	@Test
	public void test() throws InterruptedException {
		
//		BaseWebDriver.getInstance().setWebDriver("chrome","","",new HashMap<String, Object>());
//		BaseWebDriver.getInstance().getDriver().get("http://www.gmail.com");
		
		System.setProperty("webdriver.chrome.driver", new File(System.getProperty("user.dir")).getParent() + "/algorithm/drivers/mac/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://www.sonos.com");
		
		Thread.sleep(1000);
		
		
		WebElement popup = driver.findElement(By.id("bx-close-inside-1175698"));
		if(popup != null) {
			popup.click();
		}
		
		WebElement slides = driver.findElement(By.id("product-row-homepage-main"));
		WebElement ulElement = slides.findElement(By.tagName("ul"));
		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
		
		System.out.println("liElements.size() :" + liElements.size());
		
		WebElement title = driver.findElement(By.id("beside-n-headline"));
		
		System.out.println("Color :" +  title.getCssValue("color"));
		
		
	}
}
