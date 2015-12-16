package browser.main;

import java.sql.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import browser.common.thread.BrowserThread;
import browser.firefox.FirefoxThread;
import browser.google.ChromeThread;
import browser.opera.OperaThread;

/**
 * Hello world!
 *
 */
public class App 
{ 
	
	public static void runChrome(String driverPath, String url, String timeout, String timeoutExternalPage, String iterations) throws InterruptedException {
    // Optional, if not specified, WebDriver will search your path for chromedriver.

	int time = Integer.parseInt(timeout);
	int time_leave = Integer.parseInt(timeoutExternalPage);
	System.setProperty("webdriver.chrome.driver", driverPath);
	int n = Integer.parseInt(iterations);
	WebDriver driver = new ChromeDriver();
    driver.get(url);
    
	for(int i=0; n<0 || i<n; i++)
    {
      System.out.println("iteration number: "+i);
      Thread.sleep(time);  
      driver.findElement(By.partialLinkText("Go")).click();
      Thread.sleep(time_leave);  
      driver.navigate().back();
      // Let the user actually see something!
      //WebElement searchBox = driver.findElement(By.name("q"));
      //searchBox.sendKeys("ChromeDriver");
      //searchBox.submit();
      //Thread.sleep(time);  // Let the user actually see something!
    }
	driver.quit();
  }

	
    public static void main2( String[] args )
    {
    	if(args.length<3)
    	{
    		System.err.println("pass the driver path, and the url of the page to open and the number of threads");
    		//System.err.println("usage mode: path_to_web_driver url time_in_page time_out_of_page number_iterations browser_type num_threads delay_between_threads. Number_iterations can be -1. In this case the loop is infinite");
    	}
    	System.out.println("size of args:"+args.length);
    	System.out.println("parameters: ");
    	for (String s: args) {
            System.out.println(s);
        }
    	
    	
    		String driverPath = args[0];
    		String firefoxBinary = "/usr/bin/firefox";
    		String url = args[1];
    		int numThreads = Integer.parseInt(args[2]);
    		int delay = 1000;
    		String browser = "FIREFOX";
    		
    			for(int i =0; i<numThreads; i++)
    			{
    			  BrowserThread thread =null;
    			  if(browser.toUpperCase().equals("CHROME"))
    	    	  {
    				  thread = new ChromeThread(i, driverPath);
    	    	  }
    			  else if(browser.toUpperCase().equals("FIREFOX")){
    				  thread = new FirefoxThread(i, firefoxBinary);
    			  }
    			  else{
    				  thread = new OperaThread(i, driverPath);
    			  }
    			  Thread t = new Thread(thread);
    			  t.start();
    			  try {
					Thread.sleep(delay);
				  } catch (InterruptedException e) {
					System.err.println("Thread interrupted while sleeping! not cool dude!");
				  }
    			}
    		//App.runChrome(args[0],args[1], args[2], args[3], args[4]);
    }
}
