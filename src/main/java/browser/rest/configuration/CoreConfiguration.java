package browser.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"browser.service"})
public class CoreConfiguration
{
	
	//@Bean
	/*public UsersAuthzAndAuthClient getUAA()
	{
		return new UAAClient();
	}*/


}