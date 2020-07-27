package br.com.firstdatacorp.template.enums;

public enum ExceptionEnum {

	// O range 1 -> 100 reservado para excecoes padrao da api
	
	EXCEPTION							(  2, "error.server"			),
	ACCESS_DENIED_EXCEPTION 			(  3, "error.accessdenied"		),
	UNAUTHORIZED_EXCEPTION				(  4, "error.unauthorized"		),
	ITEM_NOT_FOUND_EXCEPTION			(  5, "error.notfound"			),
	INVALID_TOKEN_KEYCLOAK_EXCEPTION	(  6, "error.invalidtoken"		),
	API_EXCEPTION						(  7, "error.api"				),
	DUPLICATED_EXCEPTION				(  8, "error.duplicated"		),
	MANDATORY_FIELD_EXCEPTION			(  9, "error.mandatoryfield"	),
	ERRO_VALIDATION						( 10, "error.validation"		),
	
	// O range a partir do 101 destinado a exeções especificas da api
	
	
	;
	
	
	private final Integer code;
	private final String messageKey;
	
	private ExceptionEnum(final Integer code, final String messageKey) {
		this.code = code;
		this.messageKey = messageKey;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessageKey() {
		return messageKey;
	}

	
}
