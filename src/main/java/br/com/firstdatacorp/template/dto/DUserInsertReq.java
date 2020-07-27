package br.com.firstdatacorp.template.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DUserInsertReq {
	@NotNull(message = "Informe o email")
	@Size(min=2, message="Email deve ter pelo menos 2 caracteres")
	private String email;
	
	@NotNull(message = "Informe o nome")
	@Size(min=2, message="Nome deve ter pelo menos 2 caracteres")
	private String nome;
	
	@NotNull(message = "Informe a senha")
	@Size(min=8, message="Senha deve ter pelo menos 8 caracteres")
	private String senha;
	
	@NotNull(message = "Informe o groupId")
	private Integer groupId;
}
