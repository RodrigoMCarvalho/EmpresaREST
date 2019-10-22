package com.empresaRest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresaRest.dto.ColaboradorDTO;
import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import com.empresaRest.repository.ColaboradorRepository;
import com.empresaRest.repository.SetorRepository;

@Service
public class SetorService {

	@Autowired
	private SetorRepository repository;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	public List<Setor> findAll() {
		List<Setor> setores = repository.findAll();
		return setores;
	}
	
	public Optional<Setor> findBySetor(Integer id) {
		Optional<Setor> setor = repository.findById(id);
		return setor;
	}
	
	public List<ColaboradorDTO> findSetorById(Integer id) {
		List<Colaborador> colaboradores = colaboradorRepository.findColaboradoresBySetor(id);
<<<<<<< HEAD
		List<ColaboradorDTO> colaboradoresDto = colaboradores.stream().map(col -> new ColaboradorDTO(col))
=======
		List<ColaboradorDTO> colaboradoresDto = colaboradores.stream()
				.map(col -> new ColaboradorDTO(col))
>>>>>>> a27c05feaabc438a47836cd4c5400fd6e9258733
				.collect(Collectors.toList());
		return colaboradoresDto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
