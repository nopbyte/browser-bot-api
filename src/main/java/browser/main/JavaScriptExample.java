package browser.main;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class JavaScriptExample {
  public static void main2(String[] args) {

	System.setProperty("webdriver.chrome.driver", 
		"./chrome-drivers/ubuntu_x64");
		
	ChromeOptions options = new ChromeOptions();
	options.addArguments("window-size=1024,768");

	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	WebDriver driver = new ChromeDriver(capabilities);

	driver.get("http://google.com/");

	if (driver instanceof JavascriptExecutor) {
		((JavascriptExecutor) driver)
			.executeScript("alert('hello world');");
	}
		
  }
}