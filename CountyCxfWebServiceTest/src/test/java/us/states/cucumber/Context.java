package us.states.cucumber;


public enum Context {	
	TESTCASE_ID("Test Case Id"),
	REQUEST_FILE("Request File"), 
	REQUEST_FILE_CONTENT("Request File Conent"),
	FILE_ATTACHMENT("File Attachment"), 
	FILE_ATTACHMENT_CONTENT("File Attachment Content"),
	ELEMENT_ID("id"),	
	ELEMENT_STATE("state"),
	ELEMENT_FILENAME("filename"),
	ELEMENT_MIMETYPE("mimetype"),
	ELEMENT_FILEDATA("filedata"),
	ELEMENTS("elements"),
	REMOVE_EMPTY("remove-empty"),
	ERROR_CODES("error codes"),
	EXPECTED_RESPONSE("Expected Response"), 
	ACTUAL_RESPONSE("Actual Response"),
	STEP_ID("Step Id"), 
	STEP_STATUS("Step Status"),
	TESTCASE_STATUS("Test Case Status")
	;	
	
	public String desc;
	
	private Context(String desc) {
		this.desc = desc;
	}
	
	public static Context valueOfDesc(String desc) {
	    for (Context ctx : values()) {
	        if (ctx.desc.equals(desc)) {
	            return ctx;
	        }
	    }
	    return null;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
