package com.empresaRest.dto;

import com.empresaRest.model.Setor;
import lombok.Builder;

@Builder
public class SetorDTO {

	private Integer id;
	private String descricao;

	public SetorDTO(Setor setor) {
		this.descricao = setor.getDescricao();
		this.id = setor.getId();
	}

	public SetorDTO(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public SetorDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
