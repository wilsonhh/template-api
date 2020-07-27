package br.com.firstdatacorp.template.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ETrace {
	@Column(name = "creation_date", updatable = false)
	private Calendar dataCriacao;
	
	@Column(name = "update_date")
	private Calendar dataAlteracao;

	@Column(name = "status", nullable = false)
	private Integer status;

	public Calendar getCreationDate() {
		return dataCriacao;
	}

	public void setCreationDate(Calendar creationDate) {
		this.dataCriacao = creationDate;
	}

	public Calendar getUpdateDate() {
		return dataAlteracao;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.dataAlteracao = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
