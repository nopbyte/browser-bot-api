package browser.common.thread;


public abstract class BrowserThread implements Runnable
{
	public enum Browser {
	    CHROME, OPERA
	}
	protected Browser type;
	protected String driverPath;
	
	protected  int threadId;
	protected String instructions;
	protected boolean finished = true;
	
	protected BrowserThread(int threadId, String driverPath)
	{
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
		instructions = instructions.replaceAll("\\r|\\n", "");
		instructions = instructions.replaceAll("\\s+:\\s+","");
		String steps[] = instructions.split(";");
		for(String s:steps){
			executeStep(s.trim());
		}
		//this.quit();
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
				long howLong = Long.parseLong(s);
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


	public boolean isFinished() {
		return finished;
	}


	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	
}
