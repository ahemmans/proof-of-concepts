package demo.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import demo.ai.service.VectorStoreService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringAiPocApplication implements CommandLineRunner {

	@Autowired VectorStoreService vectorStoreService;
	
	static ConfigurableApplicationContext ctx = null;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		//SpringApplication.run(SpringAiPocApplication.class, args);
		SpringApplication app = new SpringApplication(SpringAiPocApplication.class);
		ctx = app.run(SpringAiPocApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("==== Spring AI POC Application started successfully ====");
		if (args.length > 0 && args[0].equalsIgnoreCase("repopulate")) {
			vectorStoreService.repopulateVectorStore();
		}
	}

}
