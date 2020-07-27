package br.com.firstdatacorp.template.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FieldNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FieldNotFoundException(String exception) {
		super(exception);
	}

}