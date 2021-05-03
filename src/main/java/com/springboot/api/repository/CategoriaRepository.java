package com.springboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.api.model.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{
	
	

}
