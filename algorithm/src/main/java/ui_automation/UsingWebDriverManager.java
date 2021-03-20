package ui_automation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UsingWebDriverManager {

	private WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test
	public void Seleniumtest1() {
		System.out.println("In test 1");
		driver.get("http://google.com");
		String expectedPageTitle = "Google";
		Assert.assertTrue(driver.getTitle().contains(expectedPageTitle), "Test Failed");
	}

	@Test
	public void Seleniumtest2() {
		System.out.println("In test 2");
	}

	@Test
	public void Seleniumtest3() {
		System.out.println("In test 3");
	}

	@AfterClass
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}
}
