package com.empresaRest.repository;

import com.empresaRest.model.Colaborador;
import com.empresaRest.util.ColaboradorCreator;
import com.empresaRest.util.SetorCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations ="classpath:/application-test.properties")
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ColaboradorRepositoryTest {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Test
    public void devePersistirColaboradorComSucesso() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorToBeSaved();
        colaborador.setSetor(SetorCreator.createSetor());
        Colaborador colaboradorSalvo = colaboradorRepository.save(colaborador);

        assertThat(colaboradorSalvo).isNotNull();
        assertThat(colaboradorSalvo.getId()).isNotNull();
        assertThat(colaboradorSalvo.getNome()).isNotNull();
    }

    @Test
    public void deveExcluirColaboradorComSucesso() {
        Optional<Colaborador> colaboradorASerExcluido = colaboradorRepository.findById(1);
        colaboradorASerExcluido.ifPresent(col -> colaboradorRepository.delete(col));
        Optional<Colaborador> colaborador = colaboradorRepository.findById(1);

        assertThat(colaborador).isEmpty();
    }

    @Test
    public void deveRetornarListColaboraresByCpf() {
        Optional<Colaborador> colaboradorByCpf = colaboradorRepository.findByCpf("942.655.830-67");

        colaboradorByCpf.ifPresent(colaborador -> assertThat(colaborador).isNotNull());
    }

    @Test
    public void deveRetornarTotalColaboradores() {
        Integer totalColaboradores = colaboradorRepository.totalColaboradores();

        assertThat(totalColaboradores).isEqualTo(2);
    }

    @Test
    public void deveRetornarQuantidadeColaboradoresBySetor() {
        Integer totalColaboradores = colaboradorRepository.quantidadeColaboradoresBySetor(1);

        assertThat(totalColaboradores).isEqualTo(2);
    }










}