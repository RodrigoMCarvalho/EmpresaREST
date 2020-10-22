package com.empresaRest.service;

import java.math.BigDecimal;
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
import com.empresaRest.exception.LimitAgeException;
import com.empresaRest.exception.ResourceNotFoundException;
import com.empresaRest.model.Colaborador;
import com.empresaRest.repository.ColaboradorRepository;
import com.empresaRest.repository.SetorRepository;

@Service
public class ColaboradorService {

	private ColaboradorRepository repository;
	private SetorRepository setorRepository;

	@Autowired
	public ColaboradorService(ColaboradorRepository repository, SetorRepository setorRepository) {
		this.repository = repository;
		this.setorRepository = setorRepository;
	}

	private final int MAXIMA_IDADE_PERMITIDA = 65;
	private final int MINIMA_IDADE_SETOR = 18;
	private final int CEM_PORCENTO = 100;
	private final int VINTE_PORCENTO = 20;
	
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

	public Colaborador findById(Integer id) {
		Optional<Colaborador> colaborador = repository.findById(id);
		return colaborador.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um colaborador para o ID: " + id));
	}

	public List<Colaborador> findAll() {
		return repository.findAll();
	}
	
	public Page<Colaborador> pageFindAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<ColaboradorDTO> findAllDTO() {
		List<Colaborador> colaboradores = repository.findAll();
		List<ColaboradorDTO> colaboradoresDto = colaboradores.stream()
				.map(ColaboradorDTO::new)
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
		findById(id);
		repository.deleteById(id);
	}

	private boolean verificaIdadeMaiorDeSessentaECinco(Colaborador colaborador) {
		if (colaborador.getIdade() > MAXIMA_IDADE_PERMITIDA) {
			List<Colaborador> colaboradores = repository.findAll();

			long cont = colaboradores.stream()
				.filter( col -> null != col.getIdade() && col.getIdade() > MAXIMA_IDADE_PERMITIDA)
				.count();
			cont += 1;
			
			int maximoPermitido = (colaboradores.size() * VINTE_PORCENTO) / CEM_PORCENTO;
			if (cont > maximoPermitido) {
				return true;
			}
		}
		return false;
	}

	private boolean verificaIdadeMenorDeDezoito(Colaborador colaborador) {
		if (null != colaborador.getIdade() && colaborador.getIdade() < MINIMA_IDADE_SETOR) {
			List<Colaborador> colaboradoresPorSetor = repository
					.findColaboradoresBySetor(colaborador.getSetor().getId());

			long cont = colaboradoresPorSetor.stream()
				.filter(col -> null != col.getIdade() && col.getIdade() < MINIMA_IDADE_SETOR)
				.count();
			cont += 1;

			Integer maximoPermitido = (colaboradoresPorSetor.size() * VINTE_PORCENTO) / CEM_PORCENTO;
			if (cont > maximoPermitido) {
				return true;
			}
		}
		return false;
	}

}
