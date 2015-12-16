package browser.google;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;

import browser.common.thread.BrowserThread;

public class ChromiumThread extends BrowserThread {

	private WebDriver driver = null;

	
	public ChromiumThread(int threadId, String driverPath, String binaryPath) {
		super( threadId,driverPath);
		System.setProperty("webdriver.chrome.driver", driverPath);
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("binary", binaryPath);

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		//if(true)
		//	capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		
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
		super.finished = true;
		
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
