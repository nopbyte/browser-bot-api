package browser.firefox;

import java.io.File;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;

import browser.common.thread.BrowserThread;

public class FirefoxThread extends BrowserThread {

	private WebDriver driver = null;

	public FirefoxThread(int threadId, String driverPath) {
		super( threadId,driverPath);
		FirefoxProfile profile = new FirefoxProfile();
		
		this.driver = new FirefoxDriver(new FirefoxBinary(new File(driverPath)), profile);
	}

	@Override
	public void navigateTo(String url)
	{
		driver.get(url);
		
	}

	@Override
	protected void FollowLinkByName(String name) 
	{
		driver.findElement(By.partialLinkText(name)).click();
		
	}

	@Override
	protected void navigateBack() 
	{
		driver.navigate().back();
	}

	@Override
	protected void quit() 
	{
		driver.quit();
		
	}

	@Override
	protected void refresh() {
		driver.navigate().refresh();		
	}

	@Override
	protected void runScript(String script) {

		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver)
				.executeScript(script);
		}
		
	}
	
	

	
	
	
}
