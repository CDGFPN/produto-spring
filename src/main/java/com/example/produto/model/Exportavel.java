package com.example.produto.model;

public interface Exportavel {

    String exportarParaCSV();

    String exportarParaJSON();

    String SEPARADOR_CSV = ";";
    String FORMATO_DATA = "yyyy-MM-dd";
}