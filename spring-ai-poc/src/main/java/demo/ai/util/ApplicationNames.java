package demo.ai.util;

public enum ApplicationNames {
	AIR("air"),
	PBBA("pbba")
	;
	
	private final String desc;
	
	ApplicationNames(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
	
}
