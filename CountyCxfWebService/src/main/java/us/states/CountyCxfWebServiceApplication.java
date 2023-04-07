package us.states;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CountyCxfWebServiceApplication {

	private static final Log log = LogFactory.getLog(CountyCxfWebServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CountyCxfWebServiceApplication.class, args);
		log.info("---- SpringBoot County CXF Web Service Started ----");
	}
	
}
