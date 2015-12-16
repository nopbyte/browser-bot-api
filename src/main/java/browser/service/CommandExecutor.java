package browser.service;

import browser.rest.messages.Command;
import browser.rest.messages.CommandResult;

public interface CommandExecutor 
{
	public CommandResult execute(Command c);
}
