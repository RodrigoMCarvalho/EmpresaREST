package com.empresaRest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresaRest.dto.ColaboradorDTO;
import com.empresaRest.exception.HttpMessageNotReadableException;
import com.empresaRest.exception.ResourceNotFoundException;
import com.empresaRest.exception.LimitAgeException;
import com.empresaRest.model.Colaborador;
import com.empresaRest.repository.ColaboradorRepository;
import com.empresaRest.repository.SetorRepository;

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository repository;
	
	@Autowired
	private SetorRepository setorRepository;
		
	private final int SESSENTA_CINCO = 65;
	private final int DEZOITO = 18;
	
	@Transactional
	public Colaborador save(Colaborador colaborador) {
		if (colaborador == null) {
			throw new HttpMessageNotReadableException("Favor informar os dados do colaborador");
		}
		if (verificaIdadeMaiorDeSessentaECinco(colaborador)) {
			throw new LimitAgeException("O limite de colaboradores acima de 65 anos foi atingido na empresa.");
		}
		if (verificaIdadeMenorDeDezoito(colaborador)) {
			throw new LimitAgeException("O limite de colaboradores abaixo de 18 anos foi atingido no setor.");
		}
		return repository.save(colaborador);
	}

	public Optional<Colaborador> findById(Integer id) {
		verificaSeColaboradorExiste(id);
		Optional<Colaborador> colaborador = repository.findById(id);
		return colaborador;
	}

	public List<Colaborador> findAll() {
		List<Colaborador> colaboradores = repository.findAll();
		return colaboradores;
	}
	
	public Page<Colaborador> pageFindAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<ColaboradorDTO> findAllDTO() {
		List<Colaborador> colaboradores = repository.findAll();
		List<ColaboradorDTO> colaboradoresDto = colaboradores.stream()
				.map(col -> new ColaboradorDTO(col))
				.collect(Collectors.toList());
		return colaboradoresDto;
	}
	
	public List<Colaborador> findColaboradoresBySetor(Integer id) {
		return repository.findColaboradoresBySetor(id);
	}

	@Transactional
	public Colaborador update(Colaborador colaborador) {
		return repository.save(colaborador);
	}
	
	@Transactional
	public void remove(Integer id) {
		verificaSeColaboradorExiste(id);
		repository.deleteById(id);
	}

	private void verificaSeColaboradorExiste(Integer id) {
		Optional<Colaborador> colaborador = repository.findById(id);
		colaborador.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um colaborador para o ID: " + id));
	}

	private boolean verificaIdadeMaiorDeSessentaECinco(Colaborador colaborador) {
		if (colaborador.getIdade() > SESSENTA_CINCO) {
			List<Colaborador> colaboradores = repository.findAll();

			long cont = colaboradores.stream()
				.filter( col -> null != col.getIdade() && col.getIdade() > SESSENTA_CINCO)
				.count();
			cont += 1;
			
			Integer maximoPermitido = (colaboradores.size() * 20) / 100;
			if (cont > maximoPermitido) {
				return true;
			}
		}
		return false;
	}

	private boolean verificaIdadeMenorDeDezoito(Colaborador colaborador) {
		if (null != colaborador.getIdade() && colaborador.getIdade() < DEZOITO) {
			List<Colaborador> colaboradoresPorSetor = repository
					.findColaboradoresBySetor(colaborador.getSetor().getId());

			long cont = colaboradoresPorSetor.stream()
				.filter(col -> null != col.getIdade() && col.getIdade() < DEZOITO)
				.count();
			cont += 1;

			Integer maximoPermitido = (colaboradoresPorSetor.size() * 20) / 100;
			if (cont > maximoPermitido) {
				return true;
			}
		}
		return false;
	}

}
