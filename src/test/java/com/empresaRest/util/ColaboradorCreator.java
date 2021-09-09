package com.empresaRest.util;

import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import java.util.*;

public class ColaboradorCreator {

    public static Colaborador createColaboradorToBeSaved() {
        return Colaborador.builder()
                .cpf("692.342.920-06")
                .email("email@gmail.com")
                .idade(30)
                .nome("Rodrigo")
                .telefone("111111111")
                .build();
    }

    public static Colaborador createColaboradorWithSetor() {
        return Colaborador.builder()
                .cpf("692.342.920-06")
                .email("email@gmail.com")
                .idade(30)
                .nome("Rodrigo")
                .setor(createSetor())
                .telefone("111111111")
                .build();
    }

    public static Colaborador createColaboradorToBeSavedWithSetor() {
        return Colaborador.builder()
                .cpf("692.342.920-06")
                .email("email@gmail.com")
                .idade(30)
                .nome("Rodrigo")
                .setor(createSetorNotId())
                .telefone("111111111")
                .build();
    }

    public static Colaborador createColaboradorValid() {
        return Colaborador.builder()
                .id(1)
                .cpf("692.342.920-06")
                .email("email@gmail.com")
                .idade(30)
                .nome("Rodrigo")
                .telefone("111111111")
                .setor(createSetor())
                .build();
    }

    public static List<Colaborador> createColaboradorList() {
        Colaborador col1 = Colaborador.builder().cpf("692.342.920-06").email("email@gmail.com").idade(30)
                .nome("Rodrigo").telefone("111111111").build();
        Colaborador col2 = Colaborador.builder().cpf("692.342.920-06").email("email@gmail.com").idade(7)
                .nome("Gustavo").telefone("111111111").build();
        return List.of(col1,col2);
    }

    public static Setor createSetor() {
       return Setor.builder().id(1).descricao("Setor 1").build();
    }

    public static Setor createSetorNotId() {
       return Setor.builder().descricao("Setor 1").build();
    }
}
