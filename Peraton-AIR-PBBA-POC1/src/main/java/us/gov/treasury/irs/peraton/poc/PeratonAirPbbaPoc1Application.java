package us.gov.treasury.irs.peraton.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.gov.treasury.irs.peraton.poc.fs.FilingSeasonUpdate;

@SpringBootApplication
public class PeratonAirPbbaPoc1Application implements CommandLineRunner {
	private final Log log = LogFactory.getLog(getClass());
	static ConfigurableApplicationContext ctx = null;
	
	@Autowired
	FilingSeasonUpdate fsu;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		//SpringApplication.run(PeratonAirPbbaPoc1Application.class, args);
		SpringApplication app = new SpringApplication(PeratonAirPbbaPoc1Application.class);
		ctx = app.run(PeratonAirPbbaPoc1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("---- PeratonAirPbbaPoc1Application Started ----");
		fsu.update_filing_season(args);
	}

}
