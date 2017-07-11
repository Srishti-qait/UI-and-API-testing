package training.assignment;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutomateTest

{
	Properties prop = new Properties();

	Ca com = new Ca();
	WebDriver driver;

	@BeforeTest
	public void loadOptions() throws IOException {

		InputStream input = new FileInputStream("src/main/resources/context.properties");
		prop.load(input);
		String exePath = "C:\\Users\\srishtiagarwal\\Downloads\\chromedriver_win32\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();

	}

	@Test
	public void verifyTest() throws InterruptedException, IOException {

		driver.get(prop.getProperty("URL"));
		driver.findElement(com.locate("AddComputer")).click();
		driver.findElement(com.locate("ComputerName")).sendKeys(prop.getProperty("ComputerName"));
		driver.findElement(com.locate("IntroducedDate")).sendKeys(prop.getProperty("IntroducedDate"));
		driver.findElement(com.locate("DiscontinuedDate")).sendKeys(prop.getProperty("DiscontinuedDate"));
		Select select = new Select(driver.findElement(com.locate("Company")));
		select.selectByVisibleText(prop.getProperty("Company"));
		driver.findElement(com.locate("CreateComputer")).click();
		driver.findElement(com.locate("Filter")).sendKeys(prop.getProperty("ComputerName"));
		driver.findElement(com.locate("FilterClick")).click();
		driver.findElement(By.cssSelector("#main > table > tbody > tr:nth-child(1) > td:nth-child(1) > a")).click();
		driver.findElement(com.locate("SaveComputer")).click();

	}
}
