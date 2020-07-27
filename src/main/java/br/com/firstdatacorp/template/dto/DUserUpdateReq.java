package br.com.firstdatacorp.template.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DUserUpdateReq {
	@NotNull(message = "Informe o nome")
	@Size(min=2, message="Nome deve ter pelo menos 2 caracteres")
	private String nome;
}
