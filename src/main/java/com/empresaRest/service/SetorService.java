package com.empresaRest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.empresaRest.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresaRest.dto.ColaboradorDTO;
import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import com.empresaRest.repository.ColaboradorRepository;
import com.empresaRest.repository.SetorRepository;

@Service
public class SetorService {

	private SetorRepository repository;
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	public SetorService(SetorRepository repository, ColaboradorRepository colaboradorRepository) {
		this.repository = repository;
		this.colaboradorRepository = colaboradorRepository;
	}

	public List<Setor> findAll() {
		return repository.findAll() ;
	}
	
	public Setor findBySetor(Integer id) {
		Optional<Setor> setor = repository.findById(id);
		return setor.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um setor para o ID: " + id));
	}
	
	public List<ColaboradorDTO> findSetorById(Integer id) {
		List<Colaborador> colaboradores = colaboradorRepository.findColaboradoresBySetor(id);
		List<ColaboradorDTO> colaboradoresDto = colaboradores.stream()
				.map(ColaboradorDTO::new)
				.collect(Collectors.toList());
		return colaboradoresDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
