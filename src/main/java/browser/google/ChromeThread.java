package browser.google;

import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;

import browser.common.thread.BrowserThread;

public class ChromeThread extends BrowserThread {

	private WebDriver driver = null;

	
	public ChromeThread(int threadId, String driverPath) {
		super( threadId,driverPath);
		System.setProperty("webdriver.chrome.driver", driverPath);
		
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		if(true)
			capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		
		
		this.driver = new ChromeDriver(capabilities);
		
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
		driver.close();
		super.state = BrowserThread.THREAD_STATE_FINISHED;
		
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
