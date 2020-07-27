package br.com.firstdatacorp.template.handler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.firstdatacorp.template.enums.ApplicationEnum;
import br.com.firstdatacorp.template.enums.EventActivityEnum;
import br.com.firstdatacorp.template.enums.ExceptionEnum;
import br.com.firstdatacorp.template.exception.APIException;
import br.com.firstdatacorp.template.exception.DuplicatedException;
import br.com.firstdatacorp.template.exception.InvalidTokenKeycloakException;
import br.com.firstdatacorp.template.exception.ItemNotFoundException;
import br.com.firstdatacorp.template.exception.MandatoryFieldException;
import br.com.firstdatacorp.template.exception.UnauthorizedException;
import br.com.firstdatacorp.template.model.DResponse;
import br.com.firstdatacorp.template.util.LogUtil;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger();

	private final MessageSource messageSource;

	public GlobalExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Erro interno"));
		return handleException(ExceptionEnum.EXCEPTION, new Object[] { e.getMessage() }, e, request,	HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	private ResponseEntity<Object> handleAccessDenied(AccessDeniedException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Access denied..."));
		return handleException(ExceptionEnum.ACCESS_DENIED_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UnauthorizedException.class)
	private ResponseEntity<Object> handleUnauthorized(UnauthorizedException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, EventActivityEnum.APPLICATION_AUTHENTICATION_FAILURE, "Unauthorized..."));
		return handleException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, e, request, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ItemNotFoundException.class)
	private ResponseEntity<Object> handleItemNotFound(ItemNotFoundException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Item not found..."));
		return handleException(ExceptionEnum.ITEM_NOT_FOUND_EXCEPTION, e, request, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidTokenKeycloakException.class)
	private ResponseEntity<Object> handleInvalidToken(InvalidTokenKeycloakException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, EventActivityEnum.APPLICATION_AUTHENTICATION_FAILURE, "Authentication failed..."));
		return handleException(ExceptionEnum.INVALID_TOKEN_KEYCLOAK_EXCEPTION, e, request, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(APIException.class)
	private ResponseEntity<Object> handleAPI(APIException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA,  request,	EventActivityEnum.APPLICATION_RUNTIME_ERROR, "Erro Interno"));
		return handleException(ExceptionEnum.API_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DuplicatedException.class)
	private ResponseEntity<Object> handleDuplicatedItem(DuplicatedException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Duplicated Exception"));
		return handleException(ExceptionEnum.DUPLICATED_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MandatoryFieldException.class)
	private ResponseEntity<Object> handleMandatoryField(MandatoryFieldException e, WebRequest request) {
		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, "Mandatory Fields."));
		return handleException(ExceptionEnum.MANDATORY_FIELD_EXCEPTION, new Object[] { e.getMessage() }, e, request, HttpStatus.PARTIAL_CONTENT);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		 List<String> errors = e.getBindingResult()
				                .getFieldErrors()
				                .stream()
				                .map(x -> x.getDefaultMessage())
				                .collect(Collectors.toList());

		return handleException(ExceptionEnum.ERRO_VALIDATION, new Object[] { errors }, e, request, HttpStatus.PARTIAL_CONTENT);
	}
	
    private ResponseEntity<Object> handleException(ExceptionEnum exceptionEnum, Exception e, WebRequest request, HttpStatus status) {
        LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, request, e.getMessage()));
        return handleException(exceptionEnum.getCode(), exceptionEnum.getMessageKey(), new Object[] {}, e, request, status);
    }
	
	private ResponseEntity<Object> handleException(ExceptionEnum exceptionEnum, Object[] params, Exception e, WebRequest request, HttpStatus status) {
		return handleException(exceptionEnum.getCode(), exceptionEnum.getMessageKey(), params, e, request, status);
	}
	
	private ResponseEntity<Object> handleException(Integer codigo, String messageKey, Object[] params, Exception e, WebRequest request, HttpStatus status) {
		LOGGER.info(e.getMessage());
		String message = messageSource.getMessage(messageKey, params, Locale.getDefault());
		
		ApiError apiError = new ApiError(status);
		apiError.setMessage(message);
		if(null != codigo) {
			apiError.setCodigo(codigo);
		}
		
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(DResponse.notOk(apiError.getCodigo(), apiError.getMessage()), apiError.getStatus());
	}
	
}
