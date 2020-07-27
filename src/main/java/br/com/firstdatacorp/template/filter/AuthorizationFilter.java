package br.com.firstdatacorp.template.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.com.firstdatacorp.template.enums.ApplicationEnum;
import br.com.firstdatacorp.template.enums.EventActivityEnum;
import br.com.firstdatacorp.template.model.DSecurity;
import br.com.firstdatacorp.template.security.FDSecurity;
import br.com.firstdatacorp.template.util.LogUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationFilter extends GenericFilterBean {

	private static final Logger LOGGER = LogManager.getLogger();

	FDSecurity fdSecurity;
	HandlerExceptionResolver resolver;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse respo = (HttpServletResponse) response;

		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST,
				"Starting request for :" + req.getRequestURI()));
		DSecurity fdsecurityResp = null;
		try {
			//O APIGEE fará a validação e enviarra este atributo no header identificando que a API já foi validada 
			if (req.getHeader("x-security-check") == null || !Boolean.valueOf(req.getHeader("x-security-check"))) {
				fdsecurityResp = fdSecurity.canAccessUser(req);
			}
			
			chain.doFilter(request, response);

		} catch (Exception e) {
			resolver.resolveException(req, respo, null, e);
		}

		LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST,
				"Committing request for :" + req.getRequestURI()));
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	// other methods
}