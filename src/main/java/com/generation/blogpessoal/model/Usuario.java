package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id // Define o atributo como chave primária da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Define que a chave primária (id) será gerada//
														// automaticamente// com incremento automático (auto increment).
	private Long id;

	// NOME
	@NotBlank(message = "O atributo Nome é obrigatório!") // Valida que o campo não pode ser nulo, vazio ou conter//
															// apenas espaços
	@Size(max = 255, message = "Informe seu nome.") // Define o tamanho mínimo e máximo permitido
	private String nome;

	// E-MAIL
	@Schema(example = "email@email.com.br")
	@NotBlank(message = "O Atributo Usuário é Obrigatório!")
	@Email(message = "O Atributo Usuário deve ser um email válido!")
	private String usuario;

	// Validação de senha -> vereficar se pode adicionar futuramente
	// @Pattern(
	// regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
	// message = "A senha deve conter letra maiúscula, minúscula, número e caractere
	// especial.")

	// SENHA
	@NotBlank(message = "O atributo Senha é obrigatório!")
	@Size(min = 8, max = 255, message = "Crie uma senha forte!")
	private String senha;

	// FOTO
	@Size(max = 5000, message = "O link da foto não pode ser maior 5000 caracteres.")
	private String foto;

	// Relacionemento

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties(value = "usuario", allowSetters = true)
	private List<Postagem> postagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

}
