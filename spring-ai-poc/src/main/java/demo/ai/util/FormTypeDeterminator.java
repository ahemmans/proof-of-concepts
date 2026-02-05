package demo.ai.util;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
public class FormTypeDeterminator {

	public String getFormTypeFromReceiptId(String receiptId) {
		log.debug("==== getFormTypeFromReceiptId() ==== : {}", receiptId);
		
		if (receiptId == null || receiptId.length() < 4) {
			return "UNKNOWN";
		}
	
		String prefix = getSubstringBeforeHyphen(receiptId);
		log.debug("{prefix: {}}", prefix);
		
		return switch (prefix) {
			case "1094B" -> "FORM1094B";
			case "1094C" -> "FORM1094C";
			case "1095B" -> "FORM1095B";
			case "1095C" -> "FORM1095C";
			case "1093A" -> "FORM1093A";
			case "FORM8980" -> "FORM8980";
			case "FORM8981" -> "FORM8981";
			case "FORM8985" -> "FORM8985";
			case "FORM8988" -> "FORM8988";
			case "FORM8989" -> "FORM8989";
			case "FORM14027" -> "FORM14027";        
			default -> "UNKNOWN";
		};
	}

	private String getSubstringBeforeHyphen(String input) {
	    if (input == null) {
	        return "";
	    }

	    int hyphenIndex = input.indexOf('-');

	    if (hyphenIndex == -1) {
	        return input; // No hyphen found, return original string
	    }

	    return input.substring(0, hyphenIndex);
	}
}
