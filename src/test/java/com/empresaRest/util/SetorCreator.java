package com.empresaRest.util;

import com.empresaRest.model.Setor;

public class SetorCreator {

    public static Setor createSetor() {
        return Setor.builder().descricao("Setor 1").build();
    }
}
