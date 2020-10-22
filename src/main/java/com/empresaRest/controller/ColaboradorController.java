package com.empresaRest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1")
@Api(value = "API REST Empresa")
@ExposesResourceFor(Colaborador.class)
public class ColaboradorController {

	private ColaboradorService service;

	@Autowired
	public ColaboradorController(ColaboradorService service) {
		this.service = service;
	}

	@PostMapping("/colaborador")
	@ApiOperation(value = "Salva um novo colaborador")
	@CrossOrigin
	public ResponseEntity<Colaborador> salvar(@RequestBody @Valid Colaborador colaborador, UriComponentsBuilder uriBuilder) {
		Colaborador col = service.save(colaborador);
		URI uri = uriBuilder.path("/colaborador/{id}").buildAndExpand(col.getId()).toUri();
		return ResponseEntity.created(uri).body(col);
	}

	@GetMapping("/colaboradores")
	@ApiOperation(value = "Retorna todos os colaboradores")
	@CrossOrigin
	public ResponseEntity<List<Colaborador>> buscaTodos() {
		List<Colaborador> colaboradores = service.findAll();
		return ResponseEntity.ok().body(colaboradores);
	}

	@GetMapping("/colaboradores/todosDto")
	@ApiOperation(value = "Retorna todos os colaboradores DTO")
	@CrossOrigin
	public ResponseEntity<List<ColaboradorDTO>> buscaTodosDTO() {
		List<ColaboradorDTO> colaboradoresDto = service.findAllDTO();		
		return ResponseEntity.ok().body(colaboradoresDto);
	}
	
	//http://localhost:8080/v1/colaboradores?page=0&size=6
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
	public ResponseEntity<Colaborador> buscaColaboradorPorId(@PathVariable Integer id) {
		Colaborador colaborador = service.findById(id);
		return ResponseEntity.ok().body(colaborador);
	}
	
	@GetMapping("/colaboradores/setor/{id}")
	@ApiOperation(value = "Retorna a lista de colaboradores por setor")
	@CrossOrigin
	public ResponseEntity<List<Colaborador>> buscaColaboradoresPorId(@PathVariable Integer id) {
		List<Colaborador> colaboradoresBySetor = service.findColaboradoresBySetor(id);
		return ResponseEntity.ok().body(colaboradoresBySetor);
	}
 
	@PutMapping("/colaborador")
	@ApiOperation(value = "Atualiza um colaborador")
	@CrossOrigin
	public ResponseEntity<Colaborador> atualizar(@Valid @RequestBody Colaborador colaborador) {
		Colaborador col = service.update(colaborador);
		return ResponseEntity.ok().body(col);
	}

	@DeleteMapping("/colaborador/{id}")
	@ApiOperation(value = "Remove um colaborador")
	@CrossOrigin
	public ResponseEntity<Colaborador> remover(@PathVariable Integer id) {
		service.remove(id);
		return ResponseEntity.noContent().build();
	}




}
