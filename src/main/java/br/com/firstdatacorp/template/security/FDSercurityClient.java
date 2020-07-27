package br.com.firstdatacorp.template.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.firstdatacorp.template.model.DSecurity;

@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface FDSercurityClient {
	
	public static String CLIENT = "merchant";
	public static String DOCUMENT = "document";
	public static String SERVICE_CONTRACT = "service-contract";
	public static String INSTITUTION = "instituicao";
	
	@RequestMapping(method = RequestMethod.GET, value = "/"+CLIENT+"/{instituicao}/{merchant}")
	ResponseEntity<DSecurity> findPermissionClient(@RequestHeader("Authorization") String authorization, @PathVariable("instituicao") String instituicao,@PathVariable("merchant") String merchant);

	@RequestMapping(method = RequestMethod.GET, value = "/"+DOCUMENT+"/{instituicao}/{documento}")
	ResponseEntity<DSecurity> findPermissionByCpfCnpj(@RequestHeader("Authorization") String authorization, @PathVariable("instituicao") String instituicao,@PathVariable("documento") String documento);
	
	@RequestMapping(method = RequestMethod.GET, value = "/"+SERVICE_CONTRACT+"/{instituicao}/{serviceContract}")
	ResponseEntity<DSecurity> findPermissionByServiceContract(@RequestHeader("Authorization") String authorization, @PathVariable("instituicao") String instituicao,@PathVariable("serviceContract") String serviceContract);

	@RequestMapping(method = RequestMethod.GET, value = "/"+INSTITUTION+"/{instituicao}")
	ResponseEntity<DSecurity> findPermissionInstitution(@RequestHeader("Authorization") String authorization, @PathVariable("instituicao") String instituicao);

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<DSecurity> findPermission(@RequestHeader("Authorization") String authorization);

}
