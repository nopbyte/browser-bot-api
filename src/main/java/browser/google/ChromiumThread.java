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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import browser.common.thread.BrowserThread;

public class ChromiumThread extends BrowserThread {

	private WebDriver driver = null;

	private String userDataDir = null;
	
	public ChromiumThread(int threadId, String driverPath, String binaryPath, String userData,  String callb) {
		super( threadId,driverPath);
		if(userDataDir == null && userData != null && !userData.equals(""))
			userDataDir = userData;
		if(callback == null && callb != null && !callb.equals(""))
			callback = callb;
		
		System.setProperty("webdriver.chrome.driver", driverPath);
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		//String dataDir = "/home/dp/Desktop/b"+threadId;
		ChromeOptions opt = new ChromeOptions();
		opt.setBinary(binaryPath);
		if(this.userDataDir != null && !this.userDataDir.equals(""))
		opt.addArguments("user-data-dir="+this.userDataDir);
		/*chromeOptions.put("binary", binaryPath);
		chromeOptions.put("args",);*/
		//chromeOptions.put("user-data-dir",dataDir);

		/*DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		//if(true)
		//	capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		 */
		
		this.driver = new ChromeDriver(opt);
		
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
		try{
		driver.close();
		}catch(Exception ex){
			try{
			System.err.println("Exception closing the browser attempting to quit");
			ex.printStackTrace();
			driver.quit();
			}catch(Exception ex2)
			{
				System.err.println("It seems I couldn't quit...");
				ex2.printStackTrace();
			}
		}
		
		super.state = BrowserThread.THREAD_STATE_FINISHED;
		if(callback !=null && !callback.equals(""))
			doCallback();
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
