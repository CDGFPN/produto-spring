package com.example.produto.model;

public interface Descritivel {

    String obterDescricaoCurta();

    String obterDescricaoCompleta();

    default String obterDescricaoFormatada() {
        return ">>> " + obterDescricaoCurta() + " <<<";
    }
}