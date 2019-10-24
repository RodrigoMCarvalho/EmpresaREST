package com.empresaRest.dto;

import com.empresaRest.model.Setor;

public class SetorDTO {

	private String descricao;

	public SetorDTO(Setor setor) {
		this.descricao = setor.getDescricao();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
