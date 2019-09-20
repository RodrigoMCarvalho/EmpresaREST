package com.empresaRest.dto;

import com.empresaRest.model.Colaborador;

public class ColaboradorDTO {
	
	private String nome;
	private String email;

	public ColaboradorDTO(Colaborador colaborador) {
		super();
		this.nome = colaborador.getNome();
		this.email = colaborador.getEmail();
	}

	public ColaboradorDTO() {
		super();
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
