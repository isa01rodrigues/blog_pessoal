package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRespository;

//Informa ao Spring que esta classe é um Controller REST. //Os métodos retornarão dados em formato JSON automaticamente.
@RestController

@RequestMapping("/postagem")  //Define a rota principal da API. //Exemplo: localhost:8080/postagem
 
//Permite que qualquer origem acesse a API. //Isso evita problemas de CORS ao conectar front-end e back-end
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@SuppressWarnings("unused") // Suprime avisos do compilador relacionados a variáveis não utilizadas.
	
	
	@Autowired // O Spring cria automaticamente um objeto do repositório // e injeta dentro desta variável.
	private PostagemRespository postagemRespository;
	
	 // Define que este método responderá a requisições GET.
    // Exemplo:
    // GET -> localhost:8080/postagem
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		 // Busca todas as postagens do banco usando findAll()
        // e retorna resposta HTTP 200 (OK).
		
		return ResponseEntity.ok(postagemRespository.findAll());
	}

}

