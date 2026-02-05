package demo.ai.util;

public enum TransmissionStatus {
	PROCESSING("Processing"),
	ACCEPTED("Accepted"),
	REJECTED("Rejected"),
	RECEIVED("Received"),
	BEGINNING_INTAKE_PROCESSING("Beginning Intake Processing"),
	BEGINNING_VALIDATION_PROCESSING("Beginning Validation Processing"),
	TIN_VALIDATION_SENT("TIN Validation Sent"),
	TIN_VALIDATION_COMPLETED("TIN Validation Completed"),
	ACCEPTED_WITH_WARNINGS("Accepted with Warnings"),
	ACCEPTED_WITH_ERRORS("Accepted with Errors")
	;
	
	
	private final String desc;
		
	TransmissionStatus(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
	
}
