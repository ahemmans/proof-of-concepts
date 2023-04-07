package us.gov.treasury.irs.peraton.poc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StringUtils {

	private final static Logger logger = LogManager.getLogger(StringUtils.class);
	
	public static String readFileAsString(String file) {
		String jsonStr = null;
		try {
			jsonStr = new String(Files.readAllBytes(Paths.get(file)));
			//jsonStr = Files.readString(Paths.get(file));
		} catch (IOException ioe) {
			logger.error("IOException: " + ioe.getMessage());
		}
		return jsonStr;
	}
	
}
