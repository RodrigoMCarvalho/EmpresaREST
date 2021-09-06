package com.empresaRest.controller;

import com.empresaRest.model.Setor;
import com.empresaRest.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/setores")
public class SetorController {

	private SetorService service;

	@Autowired
	public SetorController(SetorService service) {
		this.service = service;
	}

	@GetMapping
	@Operation(summary = "Busca todos os setores")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<List<Setor>> buscaTodos() {
		List<Setor> setores = service.findAll();
		return ResponseEntity.ok().body(setores);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca um setor por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não foi encontrado um setor para o ID: ")
	})
	@CrossOrigin
	public ResponseEntity<?> buscaSetor(@PathVariable Integer id) {
		Setor setor = service.findBySetor(id);
		return ResponseEntity.ok().body(setor);
	}

	@PostMapping
	@Operation(summary = "Salva um setor")
	@ApiResponse(responseCode = "201", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<?> saveSetor(@RequestBody Setor setor) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(setor.getId())
				.toUri();
		return ResponseEntity.created(uri).body(service.save(setor));
	}

}
