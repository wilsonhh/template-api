package br.com.firstdatacorp.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.firstdatacorp.template.dto.DGitResp;
import br.com.firstdatacorp.template.mapper.TemplateMapper;
import br.com.firstdatacorp.template.model.DResponse;

@RestController
@RequestMapping("/isAlive")
public class IsAliveController {

	@Autowired
	private TemplateMapper userMapper;

	@Value("${git.commit.id.abbrev}")
	private String commitId;

	@Value("${git.build.time}")
	private String buildTime;

	@Value("${git.branch}")
	private String branch;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByEmail() {
		
		DResponse<DGitResp> resp = userMapper.gitToDResponse(new DGitResp(commitId, branch, buildTime));
		return new ResponseEntity<DResponse<DGitResp>>(resp, HttpStatus.OK);
	
	}

	

}
