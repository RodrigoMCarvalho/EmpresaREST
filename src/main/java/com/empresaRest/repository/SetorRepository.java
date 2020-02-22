package com.empresaRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresaRest.model.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {

}
