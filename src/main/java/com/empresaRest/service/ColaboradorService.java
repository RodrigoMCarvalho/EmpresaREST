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

@Service
public class ColaboradorService {

	@Autowired
	private ColaboradorRepository repository;
	
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

	@Transactional
	public Colaborador update(Colaborador colaborador) {
		verificaSeColaboradorExiste(colaborador.getId());
		if (verificaIdadeMaiorDeSessentaECinco(colaborador)) {
			throw new LimitAgeException("O limite de colaboradores acima de 65 anos foi atingido na empresa.");
		}
		if (verificaIdadeMenorDeDezoito(colaborador)) {
			throw new LimitAgeException("O limite de colaboradores abaixo de 18 anos foi atingido no setor.");
		}
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
		if (colaborador.getIdade() > 65) {
			List<Colaborador> colaboradores = repository.findAll();

//			long cont = 1; // igual 1 pois entrou na condicional
//			for (Colaborador col : colaboradores) {
//				if (null != col.getIdade() && col.getIdade() > 65) {
//					cont++;
//				}
//			}
			long cont = colaboradores.stream()
				.filter( col -> null != col.getIdade() && col.getIdade() > 65).count();
			cont += 1;
			
			// double maximoPermitido = (repository.totalColaboradores() * 20) /100;
			Integer maximoPermitido = (colaboradores.size() * 20) / 100;
			if (cont > maximoPermitido) {
				return true;
			}
		}
		return false;
	}

	private boolean verificaIdadeMenorDeDezoito(Colaborador colaborador) {
		if (null != colaborador.getIdade() && colaborador.getIdade() < 18) {
			List<Colaborador> colaboradoresPorSetor = repository
					.findColaboradoresBySetor(colaborador.getSetor().getId());
//			long cont = 1;
//			for (Colaborador col : colaboradoresPorSetor) {
//				if (null != col.getIdade() && col.getIdade() < 18) {
//					cont++;
//				}
//			}
			
			long cont = colaboradoresPorSetor.stream()
				.filter(col -> null != col.getIdade() && col.getIdade() < 18)
				.count();
			cont += 1;

			// Integer maximoPermitido =(repository.quantidadeColaboradoresBySetor(colaborador.getSetor().getId())* 20 ) / 100;
			Integer maximoPermitido = (colaboradoresPorSetor.size() * 20) / 100;
			if (cont > maximoPermitido) {
				return true;
			}
		}
		return false;
	}

}
