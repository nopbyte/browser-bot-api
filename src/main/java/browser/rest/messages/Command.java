package browser.rest.messages;

import java.util.Map;

public class Command {

	private String id;
	
    private String action;
    
    private Map <String,Object> parameters;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    
    
    
 }