package browser.rest.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import browser.rest.messages.Command;
import browser.rest.messages.CommandResult;
import browser.service.CommandExecutorP2PFileAnalysis;

@RestController
public class GenericCommandController {

    
    
    @Autowired
    CommandExecutorP2PFileAnalysis exec;
    
    @RequestMapping(value="/command", method = RequestMethod.POST, consumes = "application/json")
    public CommandResult greeting(@RequestBody Command command) {
    	return exec.execute(command);
        
    }
}
