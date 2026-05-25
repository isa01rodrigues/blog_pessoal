package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Tema;

@SuppressWarnings("unused")
public interface TemaRepository extends JpaRepository<Tema, Long> {

	// Busca todos os temas cuja descrição contenha
    // o texto informado, ignorando letras maiúsculas e minúsculas
    // Exemplo:
    // descrição = "tec"
    // Pode retornar:
    // "Tecnologia"
    // "Tecnologia Web"
    // "Biotecnologia"
	
	public List<Tema>findAllByDescricaoContainingIgnoreCase(String descricao);
	//Equivale a introdução sql de SELECT * FROM tb_temas WHERE descricao LIKE "%descricao%";
	
}
