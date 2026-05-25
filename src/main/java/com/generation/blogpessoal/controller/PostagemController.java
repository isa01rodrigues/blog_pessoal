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
import com.generation.blogpessoal.repository.TemaRepository;

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
	
	@Autowired
	private TemaRepository temaRepository;
	
	// Define que este método responderá a requisições HTTP GET.
	// Exemplo:
	// GET -> localhost:8080/postagem
	@GetMapping 
	// caminho da url //postagens
	public ResponseEntity<List<Postagem>> getAll(){
		 // Busca todas as postagens do banco usando findAll()
	    // e retorna uma resposta HTTP 200 (OK).
		return ResponseEntity.ok(postagemRespository.findAll());
	}
	
	// Define a URL para buscar uma postagem específica pelo id.
	// Exemplo:
	// GET -> localhost:8080/postagem/123
	@GetMapping("/{id}") //
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		return postagemRespository.findById(id) //métodos de manipulação de dados no sql
				
				//.map metodo lambda
				.map(resposta -> ResponseEntity.ok(resposta)) // resposta -> representa o objeto recuperado dentro do Optional,ResponseEntity.ok() cria uma resposta HTTP 200 (OK) e envia o objeto encontrado para o usuário
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());  //Se o Optional estiver vazio (nenhum objeto encontrado),retorna uma resposta HTTP 404 (NOT_FOUND) sem corpo na resposta.
	}

	// Define a URL para buscar postagens pelo título.
	// Exemplo:
	// GET -> localhost:8080/postagem/titulo/java
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRespository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	// Operações do CRUD
	//
	// Representa o INSERT do SQL (criar/inserir dados)
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
		
		if (temaRepository.existsById(postagem.getTema().getId())) {
			  // Garante que o id será gerado automaticamente
			postagem.setId(null);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(postagemRespository.save(postagem));
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tema não existe!", null);
		
	}
	
	
	// Representa o UPDATE do SQL (atualizar dados)
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
	
		
		if (postagemRespository.existsById(postagem.getId())) {
			
			if (temaRepository.existsById(postagem.getTema().getId()))
				return  ResponseEntity.status(HttpStatus.OK)
						.body(postagemRespository.save(postagem));
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tema não existe!", null);		
			
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	
	
	
	// Representa o DELETE do SQL (remover dados)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRespository.findById(id);
		// Se o id não existir, retorna erro 404
		if(postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		postagemRespository.deleteById(id);
		
	}
	
	
}

