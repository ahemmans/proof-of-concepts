package demo.ai.service;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ACARulesService {

	public String getRulesFor(String appName) {
	    try {
	      var filename = String.format("classpath:/acarules/%s_rules.md", appName.toLowerCase().replace(" ", "_"));

	      return new DefaultResourceLoader()
	          .getResource(filename)
	          .getContentAsString(Charset.defaultCharset()); 
	      
	      } catch (IOException e) {
	    	  log.info("No rules found for game: " + appName);
	    	  return "";
	    }
	  }
	  
}
