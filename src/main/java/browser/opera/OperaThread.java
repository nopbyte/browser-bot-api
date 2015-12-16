package browser.opera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opera.core.systems.OperaDriver;

import browser.common.thread.BrowserThread;

public class OperaThread extends BrowserThread {

	private WebDriver driver = null;

	public OperaThread(int threadId, String driverPath) {
		super( threadId,driverPath);
		driver = new OperaDriver();
		
	}

	@Override
	public void navigateTo(String url)
	{
		driver.navigate().to(url);		
	}

	@Override
	protected void FollowLinkByName(String name) 
	{
		driver.navigate().to("http://www.google.com");
		
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
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
