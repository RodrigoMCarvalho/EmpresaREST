package com.empresaRest.service;

import com.empresaRest.exception.HttpMessageNotReadableException;
import com.empresaRest.model.Colaborador;
import com.empresaRest.repository.ColaboradorRepository;
import com.empresaRest.repository.SetorRepository;
import com.empresaRest.util.ColaboradorCreator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource(locations ="classpath:/application-test.properties")
public class ColaboradorServiceTest{

    @InjectMocks
    private ColaboradorService colaboradorService;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private SetorRepository setorRepository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        List<Colaborador> colaboradores = ColaboradorCreator.createColaboradorList();
        Colaborador colaborador = ColaboradorCreator.createColaboradorValid();
        PageImpl<Colaborador> colaboradorPage = new PageImpl<>(List.of(ColaboradorCreator.createColaboradorToBeSaved()));

        when(colaboradorRepository.save(ArgumentMatchers.any(Colaborador.class))).thenReturn(colaborador);
        when(colaboradorRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(colaborador));
    }

    @Test
    public void deveSalvarColaboradorComSucesso() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorValid();
        Colaborador colaboradorSalvo = colaboradorService.save(colaborador);

        assertThat(colaboradorSalvo.getId()).isNotNull();
        assertThat(colaboradorSalvo.getNome()).isEqualTo(colaborador.getNome());
    }

    @Test
    public void deveAtualizarColaboradorComSucesso() {
        Colaborador colaborador = ColaboradorCreator.createColaboradorValid();
        Colaborador colaboradorSalvo = colaboradorService.save(colaborador);
        colaboradorSalvo.setNome("Gusvato");
        Colaborador colaboradorAtualizado = colaboradorService.update(colaboradorSalvo);

        assertThat(colaboradorSalvo.getId()).isEqualTo(colaboradorAtualizado.getId());
        assertThat(colaboradorAtualizado.getNome()).isEqualTo("Gusvato");
    }

    @Test
    public void deveRetornarColaboradorPorId() {
        Integer idEsperado = ColaboradorCreator.createColaboradorValid().getId();
        Colaborador colaboradorSalvo = colaboradorService.findById(idEsperado);

        assertThat(colaboradorSalvo).isNotNull();
        assertThat(colaboradorSalvo.getId()).isEqualTo(idEsperado);
    }

    @Test
    public void deveLancarHttpMessageNotReadableExceptionComMensagem(){
        exception.expectMessage("Favor informar os dados do colaborador");
        colaboradorService.save(null);
    }

    @Test(expected = HttpMessageNotReadableException.class)
    public void deveLancarHttpMessageNotReadableException() {
        colaboradorService.save(null);
    }


}