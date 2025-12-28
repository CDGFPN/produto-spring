package com.example.produto.exception;

public class FornecedorNaoEncontradoException extends Exception {
    private int id;

    public FornecedorNaoEncontradoException(int id) {
        super("Fornecedor com ID " + id + " não foi encontrado no sistema.");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}