package br.com.firstdatacorp.template.enums;


public enum ActionEnum {
	PADRAO("Application Authentication"),
	AUDIT("Data Audit Trails"),
	OUTPUT_VALIDATION("Output Validation failures"),
	SUSPISCIOUS("Suspicious Behavior"),
	SESSION_MANAGEMENT("Session Management Failures"),
	APPLICATION_ERROR("Applications Erros and Events"),
	HIGH_RISK("High Risk Functionality");
	
	private final String event;
	
	private ActionEnum(final String event) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}
	
	
}
