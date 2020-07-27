package br.com.firstdatacorp.template.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.firstdatacorp.template.exception.UnauthorizedException;
import br.com.firstdatacorp.template.model.DSecurity;
import feign.FeignException;

@Component
@EnableFeignClients
public class FDSecurity {

	@Autowired
	FDSercurityClient security;

	public DSecurity canAccessUser(HttpServletRequest request) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		ResponseEntity<DSecurity> response;
		try {
			//Validacao por merchant id
			if (!StringUtils.isEmpty(request.getHeader("merchant")) || !StringUtils.isEmpty(request.getHeader("estabelecimento"))){
				response = security.findPermissionClient(request.getHeader("Authorization"), !StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") , !StringUtils.isEmpty(request.getHeader("estabelecimento")) ? request.getHeader("estabelecimento") : request.getHeader("merchant") );
				
				//Validacao por cpf/cnpj
			} else if (!StringUtils.isEmpty(request.getHeader("documento")) || !StringUtils.isEmpty(request.getHeader("document"))) {
				response = security.findPermissionByCpfCnpj(request.getHeader("Authorization"), !StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") , 
						!StringUtils.isEmpty(request.getHeader("documento")) ? request.getHeader("documento"): request.getHeader("document") );
				//validacao por service contract
			} else if (!StringUtils.isEmpty(request.getHeader("service-contract")) || !StringUtils.isEmpty(request.getHeader("codigo-contrato"))) {
				response = security.findPermissionByServiceContract(request.getHeader("Authorization"),!StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") ,
						!StringUtils.isEmpty(request.getHeader("service-contract")) ? request.getHeader("service-contract"): request.getHeader("codigo-contrato") );
				//validacao por instituicao
			} else if (!StringUtils.isEmpty(request.getHeader("institution")) || !StringUtils.isEmpty(request.getHeader("instituicao"))){
				response = security.findPermissionInstitution(request.getHeader("Authorization"), !StringUtils.isEmpty(request.getHeader("instituicao")) ? request.getHeader("instituicao"): request.getHeader("institution") );
			}else  {
				response = security.findPermission(request.getHeader("Authorization"));
			}
				
			
		} catch (FeignException e) {
			throw new UnauthorizedException();
		}
		
		return response.getBody();
	}
}
