package demo.ai.util;

import org.springframework.stereotype.Service;

@Service 
public class ApplicationDeterminator {

	public String  getApplicationNameFromFormType(String formType) {
	
	    return switch (formType) {
	        case "FORM1095A", "FORM1095B", "FORM1095C", "FORM1094B", "FORM1094C" -> "air";
	        case "FORM8980", "FORM8981", "FORM8985", "FORM8988", "FORM8989", "FORM8984", "FORM14027" -> "pbba";	        
	        default -> "UNKNOWN";
	    };
	}
	
}
