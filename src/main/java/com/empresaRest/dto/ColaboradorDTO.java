package com.empresaRest.dto;

import com.empresaRest.model.Colaborador;
import lombok.Builder;

@Builder
public class ColaboradorDTO {
	
	private String nome;
	private String email;

	public ColaboradorDTO(Colaborador colaborador) {
		super();
		this.nome = colaborador.getNome();
		this.email = colaborador.getEmail();
	}

	public ColaboradorDTO(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	public ColaboradorDTO() {
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
