package com.empresaRest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {

}
