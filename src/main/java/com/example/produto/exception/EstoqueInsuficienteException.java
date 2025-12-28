package com.example.produto.exception;

public class EstoqueInsuficienteException extends Exception {
    private String nomeProduto;
    private int quantidadeSolicitada;
    private int quantidadeDisponivel;

    public EstoqueInsuficienteException(String nomeProduto,
                                        int quantidadeSolicitada,
                                        int quantidadeDisponivel) {
        super("Estoque insuficiente para o produto '" + nomeProduto + "'. " +
                "Solicitado: " + quantidadeSolicitada + ", " +
                "Disponível: " + quantidadeDisponivel);
        this.nomeProduto = nomeProduto;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
}