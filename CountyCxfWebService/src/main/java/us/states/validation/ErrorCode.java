package us.states.validation;

public enum ErrorCode {

	ME01("Missing Element : id"),
	RV01("Required Value : id"),
	ME02("Missing Element : filename"),
	RV02("Required Value : filename"),
	ME03("Missing Element : mimetype"),
	RV03("Required Value : mimetype"),
	MT01("Invalid mime type : the file (extension) does not match the associated mime type"),
	MT02("Invalid mime type : %mt% is not an approved mime type")
	;
	
	public String desc;
	
	private ErrorCode(String desc) {
		this.desc = desc;
	}
	
	public static ErrorCode valueOfDesc(String desc) {
	    for (ErrorCode ec : values()) {
	        if (ec.desc.equals(desc)) {
	            return ec;
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
