package com.empresaRest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
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
import org.springframework.web.bind.annotation.RestController;

import com.empresaRest.dto.ColaboradorDTO;
import com.empresaRest.model.Colaborador;
import com.empresaRest.service.ColaboradorService;

@RestController
@RequestMapping("/v1")
@Api(value = "API REST Empresa")
@ExposesResourceFor(Colaborador.class)
public class ColaboradorController {
	
	@Autowired
	private ColaboradorService service;

	@PostMapping("/colaborador")
	@ApiOperation(value = "Salva um novo colaborador")
	@CrossOrigin
	public ResponseEntity<String> salvar(@Valid @RequestBody Colaborador colaborador) {
		service.save(colaborador);
		return new ResponseEntity<>("Colaborador salvo com sucesso!", HttpStatus.CREATED);
	}

	@GetMapping("/colaboradores/all")
	@ApiOperation(value = "Retorna todos os colaboradores")
	@CrossOrigin
	public ResponseEntity<List<Colaborador>> buscaTodos() {
		List<Colaborador> colaboradores = service.findAll();
		return ResponseEntity.ok().body(colaboradores);
	}

	@GetMapping("/colaboradores/todos")
	@ApiOperation(value = "Retorna todos os colaboradores DTO")
	@CrossOrigin
	public ResponseEntity<List<ColaboradorDTO>> buscaTodosDTO() {
		List<ColaboradorDTO> colaboradoresDto = service.findAllDTO();		
		return ResponseEntity.ok().body(colaboradoresDto);
	}
	
	@GetMapping("/colaboradores/page")
	@ApiOperation(value = "Retorna paginação de colaboradores")
	@CrossOrigin
	public ResponseEntity<Page<Colaborador>> pageBuscaTodos(Pageable pageable) {
		Page<Colaborador> pageColaboradores = service.pageFindAll(pageable);
		return ResponseEntity.ok().body(pageColaboradores);
	}

	@GetMapping("/colaborador/{id}")
	@ApiOperation(value = "Retorna um colaborador por ID")
	@CrossOrigin
	public ResponseEntity<Optional<Colaborador>> buscaColaboradorPorId(@PathVariable Integer id) {
		Optional<Colaborador> colaborador = service.findById(id);
		return ResponseEntity.ok().body(colaborador);
	}

	@PutMapping("/colaborador")
	@ApiOperation(value = "Atualiza um colaborador")
	@CrossOrigin
	public ResponseEntity<String> atualizar(@Valid @RequestBody Colaborador colaborador) {
		service.update(colaborador);
		return new ResponseEntity<>("Colaborador atualizado com sucesso!", HttpStatus.OK);
	}

	@DeleteMapping("/colaborador/{id}")
	@ApiOperation(value = "Remove um novo colaborador")
	@CrossOrigin
	public ResponseEntity<String> remover(@PathVariable Integer id) {
		service.remove(id);
		return new ResponseEntity<>("Colaborador removido com sucesso!", HttpStatus.OK);
	}




}
