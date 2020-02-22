package com.empresaRest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;


@Entity
@Table(name = "colaboradores")
public class Colaborador implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Campo nome obrigatório.")
	private String nome;

	@CPF(message = "CPF inválido.")
	@NotEmpty(message = "Campo CPF obrigatório.")
	@Column(unique = true)
	private String cpf;

	@Email(message = "Email inválido.")
	@NotBlank(message = "Campo email obrigatório.")
	private String email;

	@NotEmpty(message = "Campo telefone obrigatório")
	private String telefone;

	
	/* Removida a anotação @JsonManagedReference, devido ao erro relatado no link abaixo.
	 * https://stackoverflow.com/questions/49005609/spring-boot-mvc-content-type-application-jsoncharset-utf-8-not-supported*/
	 //@JsonManagedReference   //irá carregar os colaboradores por esse é o lado Managed
	@JoinColumn(name = "setor_id")
	@NotNull
	@ManyToOne
	private Setor setor;

	private Integer idade;
	
	

	public Colaborador() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

}
