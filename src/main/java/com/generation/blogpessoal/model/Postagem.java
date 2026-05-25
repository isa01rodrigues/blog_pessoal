package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity  //Define que a classe Postagem será mapeada como uma entidade do banco de dados (tabela)
@Table(name = "tb_postagens") //Responsavel por poe definir o nome da tabela e seguindo nossas nomenclaturas
public class Postagem {

    // Classe composta por atributos privados
	@Id // Define o atributo como chave primária da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Define que a chave primária (id) será gerada automaticamente com incremento automático (auto increment).
	private Long id;
		
	@NotBlank(message = "O atributo título é obrigatório!")  // Valida que o campo não pode ser nulo, vazio ou conter apenas espaços
	@Size(min = 5, max = 100, message = "O atributo título deve ter no minimo 5 e no máximo 100 caracteres.") // Define o tamanho mínimo e máximo permitido
	private String titulo;
	
	@NotBlank(message = "O atributo texto é obrigatório!")
	@Size(min = 10, max = 1000, message = "O atributo texto deve ter no minimo 10 e no máximo 1000 caracteres.")
	private String texto;
	
	@UpdateTimestamp // Atualiza automaticamente a data/hora sempre que houver alteração no registro
	private LocalDateTime data; 
	
	// Criação do relacionamento entre as tabelas
	// N:1 (Muitos para Um)
	// Muitas postagens podem estar relacionadas a apenas um tema
	
	@ManyToOne //define/mapeia o relacionamento entre entidades.
	
	// Responsável por evitar um loop infinito durante a conversão dos objetos para JSON,impedindo que Postagem chame Tema e Tema chame Postagem repetidamente
	@JsonIgnoreProperties("postagem")
	
	// Cria um atributo chamado tema do tipo Tema,
	// representando o relacionamento com a entidade Tema
	private Tema tema;
	
	//Tema → classe
	//tema → objeto/atributo/referência da classe Tema

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	
	

}
