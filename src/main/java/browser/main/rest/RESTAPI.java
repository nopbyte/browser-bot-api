package browser.main.rest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={
		"browser.rest.configuration"
		})
@EnableAutoConfiguration
public class RESTAPI {


    public static void main(String[] args) {
        SpringApplication.run(RESTAPI.class, args);
    }

}
