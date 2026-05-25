package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity  // Define que esta classe será uma entidade e será mapeada para uma tabela no banco
@Table(name = "tb_temas") // Define o nome da tabela no banco de dados
public class Tema {
	
	
	@Id // Define o atributo id como chave primária da tabela
	
	// Gera automaticamente o valor do id com incremento automático
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	// Define o atributo descricao como uma coluna da tabela
		// Não permite valor vazio ou apenas espaços em branco
	@NotBlank(message = "O atributo Descrição é obrigatório")
	private String descricao;

	// Criação do relacionamento 1:N (Um para Muitos)
	// Um tema pode estar relacionado a várias postagens
	
	@OneToMany(fetch = FetchType.LAZY, // Carrega as postagens somente quando forem necessárias
			mappedBy ="tema", // Indica que o relacionamento é controlado pelo atributo "tema" da classe Postagem
			cascade = CascadeType.REMOVE // Remove as postagens relacionadas quando um tema for removido
			)  
	// Responsável por evitar um loop infinito durante a conversão dos objetos para JSON
	@JsonIgnoreProperties(value = "tema", allowSetters = true)
	private List<Postagem> postagem;
	
	// Método getter: retorna o valor do atributo id
	public Long getId() {
		return id;
	}

	// Método setter: altera o valor do atributo id
	public void setId(Long id) {
		this.id = id;
	}

	// Método getter: retorna o valor do atributo descricao
	public String getDescricao() {
		return descricao;
	}

	// Método setter: altera o valor do atributo descricao
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
	
	

}
