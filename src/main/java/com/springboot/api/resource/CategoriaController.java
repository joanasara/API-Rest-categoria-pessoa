package com.springboot.api.resource;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.event.RecursoCriadoEvent;
import com.springboot.api.model.Categoria;
import com.springboot.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/cadastros")
public class CategoriaController {

	@Autowired
	private CategoriaRepository repository;
	
	
	@Autowired
	private ApplicationEventPublisher publishe;

	@GetMapping
	public List<Categoria> listar() {

		return repository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody  Categoria cadastro, HttpServletResponse response) {
		Categoria cadastroSalvo = repository.save(cadastro);
		publishe.publishEvent(new RecursoCriadoEvent(this, response, cadastroSalvo.getCodigo()));
	    return ResponseEntity.status(HttpStatus.CREATED).body(cadastroSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria cadastro = repository.findById(codigo).orElse(null);
		return cadastro != null ? ResponseEntity.ok(cadastro) : ResponseEntity.notFound().build();
	}
	
}