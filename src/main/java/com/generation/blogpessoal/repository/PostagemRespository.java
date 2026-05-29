package com.generation.blogpessoal.repository;
//Define o pacote onde a interface está organizada.
//O package ajuda a separar e estruturar os arquivos do projeto.

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//Importa a interface JpaRepository do Spring Data JPA.
//Ela fornece métodos prontos para manipular o banco de dados
//como salvar, buscar, atualizar e excluir registro

import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;
//Importa a classe Postagem, que representa a entidade/tabela do banco.


public interface PostagemRespository extends JpaRepository<Postagem, Long> {

// Criação da interface do repositório da entidade Postagem.
    
    // "extends JpaRepository<Postagem, Long>" significa:
    
    // Postagem → entidade que será manipulada no banco.
    
    // Long → tipo do atributo da chave primária (id) da entidade.
    
    // Ao herdar JpaRepository, métodos já são criados automaticamente:
	
	public List <Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")  String titulo);
}
