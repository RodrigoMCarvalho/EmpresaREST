package com.empresaRest.repository;

import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import com.empresaRest.util.ColaboradorCreator;
import com.empresaRest.util.SetorCreator;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations ="classpath:/application-test.properties")
public class ColaboradorRepositoryTest {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Test
    public void devePersistirColaboradorComSucesso() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorToBeSaved();
        Setor setor = setorRepository.save(SetorCreator.createSetorWithoutId());
        colaborador.setSetor(setor);
        Colaborador colaboradorSalvo = colaboradorRepository.save(colaborador);

        Assertions.assertThat(colaboradorSalvo).isNotNull();
        Assertions.assertThat(colaboradorSalvo.getId()).isNotNull();
        Assertions.assertThat(colaboradorSalvo.getNome()).isNotNull();
    }

    @Test
    public void deveExcluirColaboradorComSucesso() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorToBeSaved();
        Setor setor = setorRepository.save(SetorCreator.createSetorWithoutId());
        colaborador.setSetor(setor);
        Colaborador colaboradorSalvo = colaboradorRepository.save(colaborador);
        colaboradorRepository.delete(colaboradorSalvo);
        Optional<Colaborador> colaboradorOptional = colaboradorRepository.findById(colaboradorSalvo.getId());

        Assertions.assertThat(colaboradorOptional).isEmpty();
    }


    @Test
    public void deveRetornarListColaboraresByCpf() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorToBeSaved();
        Setor setor = setorRepository.save(SetorCreator.createSetorWithoutId());
        colaborador.setSetor(setor);
        colaboradorRepository.save(colaborador);
        Colaborador colaboradorByCpf = colaboradorRepository.findByCpf("692.342.920-06");

        Assertions.assertThat(colaboradorByCpf.getId()).isEqualTo(colaborador.getId());
    }

    @Test
    public void deveRetornarTotalColaboradores() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorToBeSaved();
        Setor setor = setorRepository.save(SetorCreator.createSetorWithoutId());
        colaborador.setSetor(setor);
        colaboradorRepository.save(colaborador);

        Integer totalColaboradores = colaboradorRepository.totalColaboradores();

        Assertions.assertThat(totalColaboradores).isEqualTo(1);
    }

    @Test
    public void deveRetornarQuantidadeColaboradoresBySetor() {
        Setor setor = setorRepository.save(SetorCreator.createSetorWithoutId());
        Colaborador colaborador = ColaboradorCreator.createColaboradorWithSetor();
        colaborador.setSetor(setor);
        colaboradorRepository.save(colaborador);

        Integer totalColaboradores = colaboradorRepository.totalColaboradores();

        Assertions.assertThat(totalColaboradores).isEqualTo(1);
    }










}