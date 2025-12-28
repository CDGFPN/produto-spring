package com.example.produto.exception;

public class DadosInvalidosException extends Exception {
    public DadosInvalidosException(String mensagem) {
        super("Dados inválidos: " + mensagem);
    }
}