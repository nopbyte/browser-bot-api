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
			return prepareThread(c);
		else if(c.getAction().toUpperCase().equals("RUN"))
			return runThread(c);
		return new CommandResult(c.getId(), "action_not_understood");
	}

	//{"action":"RUN"}
	private CommandResult runThread(Command c) {
		checkFinished();
		
		Map<String, Object> command = c.getParameters();
		BrowserThread remove = null;
		for(BrowserThread t: threadList){
		    if(t.getThreadId() == Integer.parseInt(c.getId()) ){
		    	if(t.getState().equals(BrowserThread.THREAD_STATE_RUNNING))
		    		return new CommandResult(c.getId(), "thread_is_running");
		    	else if(t.getState().equals(BrowserThread.THREAD_STATE_FINISHED)){
					return new CommandResult(c.getId(), "thread_is_finished");
		    	}
		    	else if(t.getState().equals(BrowserThread.THREAD_STATE_CREATED)){
					Thread thread = new Thread(t);
					t.setState(BrowserThread.THREAD_STATE_RUNNING);
					thread.start();
					return new CommandResult(c.getId(), "success");
		    	}
		    }
		    
		}
	
		return new CommandResult(c.getId(), "thread_not_found");
		
	}

	//{"action":"PREPARE_BROWSER", "parameters": {"chrome":{"count":2,"instructions":"open:http://localhost:8080;wait:50000"}}}
	private CommandResult prepareThread(Command c) {
		checkFinished();
		
		
		Map<String, Object> command = c.getParameters();
		for(String key: command.keySet()){
			if(key.toUpperCase().equals("CHROME")){
				Map<String,Object> browserParams = (Map<String, Object>) command.get(key);
				//for(int i = 0; i< (int)browserParams.get("count"); i++){
				System.out.println("attempting to open chromium browser from location: /usr/lib/chromium-browser/chromium-browser");
				BrowserThread t = new ChromiumThread(Integer.parseInt(c.getId()), "./chrome-drivers/ubuntu_x64",
						"/usr/lib/chromium-browser/chromium-browser",
						(String) browserParams.get("profile"),
						(String) browserParams.get("callback"));
				t.setInstructions((String) browserParams.get("instructions"));
				t.setState(BrowserThread.THREAD_STATE_CREATED);
				threadList.add(t);
				
				//}
			}
		}
		return new CommandResult(c.getId(), "success");
		
	}
	private void checkFinished() {
		List<BrowserThread> rem = new LinkedList<BrowserThread>();
		for(BrowserThread t : threadList)
			if(t.getState().equals(BrowserThread.THREAD_STATE_FINISHED))
				rem.add(t);
		
		threadList.removeAll(rem);
		
	}
	
}
