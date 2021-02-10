package ui_automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
		
		 // Waiting 30 seconds for an element to be present on the page, checking
		   // for its presence once every 5 seconds.
		   Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       .withTimeout(60, TimeUnit.SECONDS)
		       .pollingEvery(5, TimeUnit.SECONDS)
		       .ignoring(NoSuchElementException.class);

		   WebElement popup = wait.until(new Function<WebDriver, WebElement>() {
		     public WebElement apply(WebDriver driver) {
		       return driver.findElement(By.id("bx-close-inside-1175698"));
		     }
		   });
		
		
		//WebElement popup = driver.findElement(By.id("bx-close-inside-1175698"));
		if(popup != null) {
			popup.click();
		}
		
		WebElement slides = driver.findElement(By.id("product-row-homepage-main"));
		WebElement ulElement = slides.findElement(By.tagName("ul"));
		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
		
		System.out.println("liElements.size() :" + liElements.size());
		
		//WebElement title = driver.findElement(By.id("beside-n-headline"));
		WebElement title = driver.findElement(By.xpath("//*[@id='beside-n-headline']"));
	
		
		System.out.println("Color :" +  title.getCssValue("color"));
		
		WebElement div = driver.findElement(By.xpath("//div[@id='product-row-homepage-main']"));
		
		System.out.println(div.getAttribute("class"));
		
		WebElement ul_element = driver.findElement(By.xpath("//div[@id='product-row-homepage-main']/ul[1]"));
		WebElement li_element = driver.findElement(By.xpath("//div[@id='product-row-homepage-main']/ul[1]/li[1]"));

	}
}
