package demo.ai.util;

public enum FormTypes {
	FORM1095A("FORM1095A"),
	FORM1095B("FORM1095B"),
	FORM1095C("FORM1095C"),
	FORM1094B("FORM1094B"),
	FORM1094C("FORM1094C"),
	FORM8980("FORM8980"), 
	FORM8981("FORM8981"), 
	FORM8985("FORM8985"), 
	FORM8988("FORM8988"), 
	FORM8989("FORM8989"), 
	FORM8984("FORM8984"), 
	FORM14027("FORM14027")
	;
	
		
	private final String desc;

	FormTypes(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
	
}
