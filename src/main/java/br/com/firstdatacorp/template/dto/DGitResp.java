package br.com.firstdatacorp.template.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DGitResp {
	private String commit;
	private String branch;
	private String build;
}
