package us.states.validation;

public enum MimeType {

	TXT("text/plain"),
	XML("text/xml"),
	XML2("application/xml"),
	HTML("text/html"),
	PDF("application/pdf"),
	ZIP("application/zip"),
	JSON("application/json"),	
	//JPEG("image/jpeg"),
	//JPG("image/jpeg"),
	//GIF("image/gif"),
	//PNG("image/png"),
	OTHER("application/octet-stream")
	;
	
	public String text;
	
	private MimeType(String text) {
		this.text = text;
	}
	
	public static MimeType valueOfText(String text) {
	    for (MimeType mt : values()) {
	        if (mt.text.equals(text)) {
	            return mt;
	        }
	    }
	    return null;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
