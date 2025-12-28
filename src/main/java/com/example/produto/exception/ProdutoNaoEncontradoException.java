package com.example.produto.exception;

public class ProdutoNaoEncontradoException extends Exception {
    private int id;

    public ProdutoNaoEncontradoException(int id) {
        super("Produto com ID " + id + " não foi encontrado no sistema.");
        this.id = id;
    }

    public int getId() {
        return id;
    }
}