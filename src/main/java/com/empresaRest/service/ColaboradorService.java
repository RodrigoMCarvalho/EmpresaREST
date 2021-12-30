package com.empresaRest.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.empresaRest.exception.CpfException;
import com.empresaRest.model.Setor;
import com.empresaRest.utils.Utils;
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

	private final int MAXIMA_IDADE_PERMITIDA = 65;
	private final int MINIMA_IDADE_SETOR = 18;
	private final int CEM_PORCENTO = 100;
	private final int VINTE_PORCENTO = 20;

	private ColaboradorRepository colaboradorRepository;
	private SetorRepository setorRepository;

	@Autowired
	public ColaboradorService(ColaboradorRepository colaboradorRepository, SetorRepository setorRepository) {
		this.colaboradorRepository = colaboradorRepository;
		this.setorRepository = setorRepository;
	}

	@Transactional
	public Colaborador save(Colaborador colaborador) {
		if (Objects.isNull(colaborador)) {
			throw new HttpMessageNotReadableException("Favor informar os dados do colaborador");
		}
		if(verificaCpfCadastrado(colaborador.getCpf())) {
			throw new CpfException("CPF informado já consta cadastrado na empresa");
		}
		if (verificaIdadeMaiorDe65(colaborador)) {
			throw new LimitAgeException("O limite de colaboradores acima de 65 anos foi atingido na empresa.");
		}
		if (verificaIdadeMenorDe18(colaborador)) {
			throw new LimitAgeException("O limite de colaboradores abaixo de 18 anos foi atingido no setor.");
		}
		return colaboradorRepository.save(colaborador);
	}

	public Colaborador findById(Integer id) {
		Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
		return colaborador.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um colaborador para o ID: " + id));
	}

	public List<Colaborador> findAll() {
		return colaboradorRepository.findAll();
	}
	
	public Page<Colaborador> pageFindAll(Pageable pageable) {
		return colaboradorRepository.findAll(pageable);
	}

	public List<ColaboradorDTO> findAllDTO() {
		List<Colaborador> colaboradores = colaboradorRepository.findAll();
		return colaboradores.stream()
				.map(ColaboradorDTO::new)
				.collect(Collectors.toList());
	}
	
	public List<Colaborador> findColaboradoresBySetor(Integer id) {
		return colaboradorRepository.findColaboradoresBySetor(id);
	}

	@Transactional
	public Colaborador update(Colaborador colaborador) {
		return colaboradorRepository.save(colaborador);
	}
	
	@Transactional
	public void remove(Integer id) {
		findById(id);
		colaboradorRepository.deleteById(id);
	}

	private boolean verificaCpfCadastrado(String cpf) {
		return colaboradorRepository.findByCpf(cpf).isPresent();
	}

	private boolean verificaIdadeMaiorDe65(Colaborador colaborador) {
		if (Objects.nonNull(colaborador.getIdade()) && colaborador.getIdade() > MAXIMA_IDADE_PERMITIDA) {
			Setor setor = setorRepository.findById(colaborador.getSetor().getId()).get();

			int totalColaboradoresDoSetor = setor.getColaboradores().size();
			long acimaDaIdadePermitida = setor.getColaboradores().stream().filter(col -> col.getIdade() > MAXIMA_IDADE_PERMITIDA).count();

			if (totalColaboradoresDoSetor > 0) {
				BigDecimal percentualComMais65 = Utils.calcularPorcentual(BigDecimal.valueOf(totalColaboradoresDoSetor), BigDecimal.valueOf(acimaDaIdadePermitida));
				if (percentualComMais65.compareTo(BigDecimal.valueOf(VINTE_PORCENTO)) > 0 ) return true;
			}
		}
		return false;
	}

	private boolean verificaIdadeMenorDe18(Colaborador colaborador) {
		if (Objects.nonNull(colaborador.getIdade()) && colaborador.getIdade() < MINIMA_IDADE_SETOR) {
			Setor setor = setorRepository.findById(colaborador.getSetor().getId()).get();

			int totalColaboradoresDoSetor = setor.getColaboradores().size();
			long abaixoDaIdadePermitida = setor.getColaboradores().stream().filter(col -> col.getIdade() < MINIMA_IDADE_SETOR).count();

			if (totalColaboradoresDoSetor > 0) {
				BigDecimal percentualComMenosDe18 = Utils.calcularPorcentual(BigDecimal.valueOf(totalColaboradoresDoSetor), BigDecimal.valueOf(abaixoDaIdadePermitida));
				if (percentualComMenosDe18.compareTo(BigDecimal.valueOf(VINTE_PORCENTO)) > 0 ) return true;
			}
		}
		return false;
	}

}
