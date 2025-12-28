package com.example.produto.model;

public interface Avaliavel {

    void avaliar(double nota);

    double obterAvaliacaoMedia();

    int obterTotalAvaliacoes();

    default String obterClassificacao() {
        double media = obterAvaliacaoMedia();
        if (media >= 4.5) return "Excelente";
        if (media >= 3.5) return "Bom";
        if (media >= 2.5) return "Regular";
        if (media >= 1.5) return "Ruim";
        return "Péssimo";
    }
}