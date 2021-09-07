package com.empresaRest.util;

import com.empresaRest.model.Setor;

public class SetorCreator {

    public static Setor createSetor() {
        return Setor.builder().id(1).descricao("Setor 1").build();
    }
}
