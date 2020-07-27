package br.com.firstdatacorp.template.exception;


public class MandatoryFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MandatoryFieldException(String message) {
		super(message);
	}

	public MandatoryFieldException(Throwable cause) {
		super(cause);
	}
}
