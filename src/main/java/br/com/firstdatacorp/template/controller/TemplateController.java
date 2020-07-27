package br.com.firstdatacorp.template.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import br.com.firstdatacorp.template.domain.EUser;
import br.com.firstdatacorp.template.dto.DUserInsertReq;
import br.com.firstdatacorp.template.dto.DUserResp;
import br.com.firstdatacorp.template.dto.DUserUpdateReq;
import br.com.firstdatacorp.template.mapper.TemplateMapper;
import br.com.firstdatacorp.template.model.DPage;
import br.com.firstdatacorp.template.model.DResponse;
import br.com.firstdatacorp.template.service.TemplateService;

@RestController
@RequestScope
@RequestMapping("/template/v1")
public class TemplateController {

	@Autowired
	private TemplateService service;

	@Autowired	
	private TemplateMapper userMapper;

	@RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUsuarios(Pageable pageable) {

		Page<EUser> users = service.findAll(pageable);
		DPage<DUserResp> lResp = userMapper.pageUserToDPageUser(users);
		return new ResponseEntity<DPage<DUserResp>>(lResp, HttpStatus.OK);
	}

	@RequestMapping(value = "/usuarios/nome/{nome}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByName(@PathVariable("nome") String nome,
			HttpServletRequest request, Pageable pageable) {
		Page<EUser> users = service.findByName(nome, pageable);
		DPage<DUserResp> resp = userMapper.pageUserToDPageUser(users);
		return new ResponseEntity<DPage<DUserResp>>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuarios/email/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email,
			HttpServletRequest request, Pageable pageable) {
		EUser user = service.findByEmail(email);
		DResponse<DUserResp> resp = userMapper.userToDResponse(userMapper.userToUserDTO(user));
		return new ResponseEntity<DResponse<DUserResp>>(resp, HttpStatus.OK);
	}

	@PostMapping("/usuarios")
	public void saveUser(@Valid @RequestBody DUserInsertReq reqDto) {
		service.save(userMapper.dUserInsertReqToEUser(reqDto));
	}
	
	@PutMapping("/usuarios/{id}")
	public void updateUser(@RequestBody DUserUpdateReq dto, @PathVariable int id) {
		service.update(id, dto.getNome());
	}
	
	@DeleteMapping("/usuarios/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		service.remove(id);
	}

}
