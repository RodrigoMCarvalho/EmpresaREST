package com.empresaRest.util;

import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;

public class ColaboradorCreator {

    public static Colaborador createColaboradorToBeSaved() {

        return Colaborador.builder()
                .cpf("692.342.920-06")
                .email("email@gmail.com")
                .idade(30).nome("Rodrigo")
                .telefone("111111111")
                .build();
    }

    public static Setor createSetor() {
       return Setor.builder().id(1).descricao("Setor 1").build();
    }
}
