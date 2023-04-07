package us.gov.treasury.irs.peraton.poc.util;

public enum Refactor {
	PRE_MERGE("pre-merge"),
	POST_MERGE("post-merge");
	
	public String desc;
	
	private Refactor(String desc) {
		this.desc = desc;
	}
	
	public static Refactor valueOfDesc(String desc) {
	    for (Refactor ctx : values()) {
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
