package br.com.firstdatacorp.template.enums;


public enum ApplicationEnum {
	SBA("UAID-04975", "Brazilian Surround Services Application"),
	TICKET_TRANSACTION("UAID-04792", "Ticket Transaction Monitor BRA");
	
	private final String uaid;
	
	private final String name;
	
	
	private ApplicationEnum(final String uaid, final String name) {
		this.uaid = uaid;
		this.name = name;
	}


	public String getUaid() {
		return uaid;
	}


	public String getName() {
		return name;
	}

}
