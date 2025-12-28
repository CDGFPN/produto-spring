package com.example.produto.exception;

public class OpcaoInvalidaException extends Exception {
    private int opcao;

    public OpcaoInvalidaException(int opcao) {
        super("Opção inválida: " + opcao + ". Por favor, escolha uma opção válida do menu.");
        this.opcao = opcao;
    }

    public int getOpcao() {
        return opcao;
    }
}