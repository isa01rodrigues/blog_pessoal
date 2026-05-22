package com.generation.blogpessoal.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRespository;

import jakarta.validation.Valid;

//Informa ao Spring que esta classe é um Controller REST. //Os métodos retornarão dados em formato JSON automaticamente.
@RestController

@RequestMapping("/postagens")  //Define a rota principal da API. //Exemplo: localhost:8080/postagem
 
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		return postagemRespository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRespository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	//Representa o Insert no SQL -Inserir
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		
		postagem.setId(null);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postagemRespository.save(postagem));
	}
	
	//Equivalente UPDATE no Sql - Atualizar
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
		return postagemRespository.findById(postagem.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
					.body(postagemRespository.save(postagem)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRespository.findById(id);
		
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRespository.deleteById(id);
		
	}
	
	
}

