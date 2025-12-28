package com.example.produto.exception;

public class PrecoInvalidoException extends Exception {
    private double preco;

    public PrecoInvalidoException(double preco) {
        super("Preço inválido: R$ " + String.format("%.2f", preco) +
                ". O preço deve ser maior que zero.");
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }
}
