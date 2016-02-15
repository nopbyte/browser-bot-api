package browser.common.thread;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public abstract class BrowserThread implements Runnable
{
	public static String THREAD_STATE_CREATED = "CREATED";
	public static String THREAD_STATE_RUNNING = "RUNNING";
	public static String THREAD_STATE_FINISHED = "FINISHED";
	public static String THREAD_STATES[] = {THREAD_STATE_CREATED,THREAD_STATE_RUNNING,THREAD_STATE_FINISHED};
	public enum Browser {
	    CHROME, OPERA
	}
	protected Browser type;
	protected String driverPath;
	
	protected  int threadId;
	protected String instructions;
	protected String state = THREAD_STATE_CREATED;
	
	//callback after the thread finishes.
	protected String callback = null;
	
	protected BrowserThread(int threadId, String driverPath)
	{
		System.out.println("preparing thread with id: "+threadId);
		this.threadId = threadId;
		this.driverPath = driverPath;

	}
	

	public String getInstructions() {
		return instructions;
	}


	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}


	@Override
	public void run() 
	{
		System.out.println("running thread with id: "+threadId);
		instructions = instructions.replaceAll("\\r|\\n", "");
		instructions = instructions.replaceAll("\\s+:\\s+","");
		String steps[] = instructions.split(";");
		for(String s:steps){
			try{
				executeStep(s.trim());
			}catch(Exception ex){
				System.out.println("Exception ocurred during the excution of command "+s.trim()+" calling the callback and calling it a day... sorry...");
				ex.printStackTrace();
				doCallback();
			}
		}
		//this.quit();
	}
	
	protected void doCallback() {
		this.state = THREAD_STATE_FINISHED;
		try{
			ResponseEntity<Object> responseEntity= null;
	    		String url = this.callback;
	        HttpHeaders header = new HttpHeaders();
	       
	        RestTemplate restTemplate = new RestTemplate();
	        responseEntity = restTemplate.getForEntity(url, Object.class);
	        
	        if(!responseEntity.getStatusCode().equals(HttpStatus.OK))
	        	System.err.println("Could not call callback! "+callback);
	        else
	        	System.out.println("calling callback? "+callback);
		}catch(Exception ex){
			//TODO fix this dirty, ugly, horrible hack... 
		}
	}
	//language:
	/*
	 * open:http://localhost:8080;
	 * wait:3000;
	 * js:window.alert('hi')
	 * wait:3000;
	 * close;
	 * 
	 * */
	private void executeStep(String s) {
		if(s.startsWith("open:")){
			navigateTo(s.replaceAll("open:", ""));
		}
		else if(s.startsWith("wait:")){
			try {
				s = s.replaceAll("wait:","");
				long howLong = Long.parseLong(s)*1000;
				Thread.sleep(howLong);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if(s.startsWith("js:")){
			this.runScript(s.replaceAll("js:",""));
		}
		else if(s.startsWith("close")){
			this.quit();
		}
		
		
		
		
	}

	protected abstract void refresh();

	public abstract void navigateTo(String url);
	
	protected abstract void FollowLinkByName(String name);
	
	protected abstract void navigateBack();
	
	protected abstract void quit();
	
	protected abstract void runScript(String script);

	public String getState(){
		return state;
	}
	
	public void setState(String s){
		this.state = s;
	}


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
	
	
}
