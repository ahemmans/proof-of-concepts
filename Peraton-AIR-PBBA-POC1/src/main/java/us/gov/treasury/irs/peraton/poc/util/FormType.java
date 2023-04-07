package us.gov.treasury.irs.peraton.poc.util;

public enum FormType {
	ELECTRONIC("electronic"),
	REFACTOR("refactor"),
	TEN95A("1095A"),
	PAPER("paper"),
	WO("wo"),
	BC("bc")	
	;
	
	public String desc;
	
	private FormType(String desc) {
		this.desc = desc;
	}
	
	public static FormType valueOfDesc(String desc) {
	    for (FormType ctx : values()) {
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
