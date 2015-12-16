package browser.rest.messages;

public class CommandResult {
	String status;
	String command_id;
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCommand_id() {
		return command_id;
	}
	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}
	public CommandResult(String command_id, String status) {
		this.command_id = command_id;
		this.status = status;
	}
	
}
