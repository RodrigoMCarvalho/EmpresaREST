package com.empresaRest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer>{
	
	public Colaborador findByCpf(String cpf);
	public Optional<Colaborador> findById(Integer id);
	
	@Query("SELECT COUNT(*) FROM Colaborador c")
	public Integer totalColaboradores();

	@Query("SELECT c FROM Colaborador c WHERE c.setor.id =:id")
	public List<Colaborador> findColaboradoresBySetor(@Param(value = "id") Integer id);
	
	@Query("SELECT COUNT(c) FROM Colaborador c WHERE c.setor.id =:id")
	public Integer quantidadeColaboradoresBySetor(@Param(value = "id") Integer id);
	
}
