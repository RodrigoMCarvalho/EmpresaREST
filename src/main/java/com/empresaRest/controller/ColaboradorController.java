package com.empresaRest.controller;

import com.empresaRest.dto.ColaboradorDTO;
import com.empresaRest.model.Colaborador;
import com.empresaRest.service.ColaboradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/colaboradores")
@ExposesResourceFor(Colaborador.class)
@Tag(name = "Endpoint de Colaboradores")
public class ColaboradorController {

	private ColaboradorService colaboradorService;

	@Autowired
	public ColaboradorController(ColaboradorService colaboradorService) {
		this.colaboradorService = colaboradorService;
	}

	@PostMapping
	@Operation(summary =  "Salva um novo colaborador")
	@ApiResponse(responseCode = "201", description = "Operação realizada com sucesso.")
	public ResponseEntity<Colaborador> salvar(@RequestBody @Valid Colaborador colaborador) {
		Colaborador col = colaboradorService.save(colaborador);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(col.getId())
				.toUri();
		return ResponseEntity.created(uri).body(col);
	}

	@GetMapping
	@Operation(summary =  "Retorna todos os colaboradores")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<List<Colaborador>> buscaTodos() {
		List<Colaborador> colaboradores = colaboradorService.findAll();
		return ResponseEntity.ok().body(colaboradores);
	}

	@GetMapping("/todosDto")
	@Operation(summary =   "Retorna todos os colaboradores DTO")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<List<ColaboradorDTO>> buscaTodosDTO() {
		List<ColaboradorDTO> colaboradoresDto = colaboradorService.findAllDTO();
		return ResponseEntity.ok().body(colaboradoresDto);
	}
	
	//http://localhost:8080/v1/colaboradores?page=0&size=6
	@GetMapping("/page")
	@Operation(summary = "Retorna paginação de colaboradores")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<Page<Colaborador>> pageBuscaTodos(@ParameterObject Pageable pageable) {
		Page<Colaborador> pageColaboradores = colaboradorService.pageFindAll(pageable);
		return ResponseEntity.ok().body(pageColaboradores);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Retorna um colaborador por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não foi encontrado um colaborador para o ID:.")
	})
	@CrossOrigin
	public ResponseEntity<Colaborador> buscaColaboradorPorId(@PathVariable Integer id) {
		Colaborador colaborador = colaboradorService.findById(id);
		return ResponseEntity.ok().body(colaborador);
	}
	
	@GetMapping("/setor/{id}")
	@Operation(summary = "Retorna a lista de colaboradores por setor")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<List<Colaborador>> buscaColaboradoresPorId(@PathVariable Integer id) {
		List<Colaborador> colaboradoresBySetor = colaboradorService.findColaboradoresBySetor(id);
		return ResponseEntity.ok().body(colaboradoresBySetor);
	}
 
	@PutMapping("/{id}")
	@Operation(summary = "Atualiza um colaborador")
	@ApiResponse(responseCode = "200", description = "Operação realizada com sucesso.")
	@CrossOrigin
	public ResponseEntity<Colaborador> atualizar(@PathVariable Integer id,
												 @RequestBody @Valid Colaborador colaborador) {
		Colaborador colaboradorSalvo = colaboradorService.findById(id);
		colaborador.setId(colaboradorSalvo.getId());
		Colaborador col = colaboradorService.update(colaborador);
		return ResponseEntity.ok().body(col);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Remove um colaborador")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Operação realizada com sucesso."),
			@ApiResponse(responseCode = "404", description = "Não foi encontrado um colaborador para o ID: ")
	})
	@CrossOrigin
	public ResponseEntity<Colaborador> remover(@PathVariable Integer id) {
		colaboradorService.remove(id);
		return ResponseEntity.noContent().build();
	}




}
