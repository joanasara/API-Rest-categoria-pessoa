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
import com.springboot.api.model.Pessoa;
import com.springboot.api.repository.PessoaRepository;

@RestController
@RequestMapping("/{pessoa}")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepositorio;
	
	@Autowired
	private  ApplicationEventPublisher publisher;
	
	
	
	@GetMapping
	public List<Pessoa>listar(){
		
		return pessoaRepositorio.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody  Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalvo =pessoaRepositorio.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvo.getCodigo()));
	    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoa =  pessoaRepositorio.findById(codigo).orElse(null);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
}
