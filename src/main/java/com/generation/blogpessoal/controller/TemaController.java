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

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/tema")  // informações vem da pasta tema
@CrossOrigin (origins = "*", allowedHeaders = "*")//Permite que qualquer origem acesse a API. //Isso evita problemas de CORS ao conectar front-end e back-end
public class TemaController {

	// @Autowired faz a injeção de dependência
	// O Spring cria automaticamente uma instância do TemaRepository
	// e disponibiliza para ser utilizada nesta variável
	@Autowired 
	private TemaRepository temaRepository; //Variável que recebe o objeto do repositório,permitindo acesso aos métodos de banco de dados (save, findAll, findById, deleteById...)
	
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	// Busca todos os temas cadastrados
	@GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable Long id){
        return temaRepository.findById(id)
        		// Se encontrar o tema
                // retorna HTTP 200 (OK) + objeto encontrado
        		
            .map(resposta -> ResponseEntity.ok(resposta)) //resposta -> representa o objeto recuperado dentro do Optional,ResponseEntity.ok() cria uma resposta HTTP 200 (OK) e envia o objeto encontrado para o usuário
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); //Se o Optional estiver vazio (nenhum objeto encontrado),retorna uma resposta HTTP 404 (NOT_FOUND) sem corpo na resposta.
    }
	
	// Define a URL para buscar postagens pelo título.
		// Exemplo:
		// GET -> localhost:8080/postagem/descricao/java
	@GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Tema>> getAllByDescricao(@PathVariable 
    String descricao){
        return ResponseEntity.ok(temaRepository
            .findAllByDescricaoContainingIgnoreCase(descricao));
    }
	
	// Representa o INSERT do SQL (criar/inserir dados)
	 @PostMapping
	    public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
	    	
		// Garante que o id será gerado automaticamente
	    	tema.setId(null);
	    	
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(temaRepository.save(tema));
	    }
	 
	 
	// Representa o UPDATE do SQL (atualizar dados) 
	
	 @PutMapping
	    public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
	        return temaRepository.findById(tema.getId())
	            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
	            .body(temaRepository.save(tema)))
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	    }
	 
	 
	// Representa o DELETE do SQL (remover dados)
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        Optional<Tema> tema = temaRepository.findById(id);
	        
	        if(tema.isEmpty())
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	        
	        temaRepository.deleteById(id);              
	    }
	 
}
