package browser.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import browser.common.thread.BrowserThread;
import browser.google.ChromeThread;
import browser.google.ChromiumThread;
import browser.rest.messages.Command;
import browser.rest.messages.CommandResult;

@Service
public class CommandExecutorP2PFileAnalysis implements CommandExecutor{

	List<BrowserThread> threadList;
	
	public CommandExecutorP2PFileAnalysis() 
	{
		threadList = new LinkedList<BrowserThread>();
	}
	@Override
	public CommandResult execute(Command c) {
		if(c.getAction().toUpperCase().equals("PREPARE_BROWSER"))
			return prepareBrowsers(c);
		else if(c.getAction().toUpperCase().equals("RUN"))
			return runBrowsers(c);
		return new CommandResult(c.getId(), "action_not_understood");
	}

	//{"action":"RUN"}
	private CommandResult runBrowsers(Command c) {
		CommandResult r = finished(c);
		if(r!=null)
				return r;
		
		Map<String, Object> command = c.getParameters();
		for(BrowserThread t: threadList){
			Thread thread = new Thread(t);
			t.setFinished(false);
			thread.start();
		}
		return new CommandResult(c.getId(), "success");
		
	}

	//{"action":"PREPARE_BROWSER", "parameters": {"chrome":{"count":2,"instructions":"open:http://localhost:8080;wait:50000"}}}
	private CommandResult prepareBrowsers(Command c) {
		CommandResult r = finished(c);
		if(r!=null)
				return r;
		threadList = new LinkedList<BrowserThread>();
		Map<String, Object> command = c.getParameters();
		for(String key: command.keySet()){
			if(key.toUpperCase().equals("CHROME")){
				Map<String,Object> browserParams = (Map<String, Object>) command.get(key);
				for(int i = 0; i< (int)browserParams.get("count"); i++){
					System.out.println("attempting to open chromium browser from location: /usr/lib/chromium-browser/chromium-browser");
					BrowserThread t = new ChromiumThread((i), "./chrome-drivers/ubuntu_x64","/usr/lib/chromium-browser/chromium-browser");
					t.setInstructions((String) browserParams.get("instructions"));
					threadList.add(t);
				}
			}
		}
		return new CommandResult(c.getId(), "success");
		
	}
	private CommandResult finished(Command c) {
		boolean finished = true;
		for(BrowserThread t : threadList){
			finished &= t.isFinished();
		} 
		if(!finished)
			return  new CommandResult(c.getId(), "error: not finished");
		return null;
	}

}
