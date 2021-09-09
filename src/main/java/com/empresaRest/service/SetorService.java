package com.empresaRest.service;

import com.empresaRest.dto.ColaboradorDTO;
import com.empresaRest.exception.ResourceNotFoundException;
import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import com.empresaRest.repository.ColaboradorRepository;
import com.empresaRest.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SetorService {

	private SetorRepository setorRepository;
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	public SetorService(SetorRepository repository, ColaboradorRepository colaboradorRepository) {
		this.setorRepository = repository;
		this.colaboradorRepository = colaboradorRepository;
	}

	@Transactional
	public Setor save(Setor setor) {
		return setorRepository.save(setor) ;
	}

	public List<Setor> findAll() {
		return setorRepository.findAll() ;
	}
	
	public Setor findBySetor(Integer id) {
		Optional<Setor> setor = setorRepository.findById(id);
		return setor.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um setor para o ID: " + id));
	}
	
	public List<ColaboradorDTO> findSetorById(Integer id) {
		List<Colaborador> colaboradores = colaboradorRepository.findColaboradoresBySetor(id);
		return colaboradores.stream()
				.map(ColaboradorDTO::new)
				.collect(Collectors.toList());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
