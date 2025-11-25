package com.example.produto.model;

public enum Categoria {
    ELETRONICOS("Eletrônicos", 15.0),
    ALIMENTOS("Alimentos", 5.0),
    VESTUARIO("Vestuário", 20.0),
    LIVROS("Livros", 10.0),
    OUTROS("Outros", 8.0);

    private final String descricao;
    private final double percentualDesconto;

    // Construtor do enum
    Categoria(String descricao, double percentualDesconto) {
        this.descricao = descricao;
        this.percentualDesconto = percentualDesconto;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    // Método para obter categoria a partir de um número
    public static Categoria porOpcao(int opcao) {
        switch (opcao) {
            case 1:
                return ELETRONICOS;
            case 2:
                return ALIMENTOS;
            case 3:
                return VESTUARIO;
            case 4:
                return LIVROS;
            case 5:
            default:
                return OUTROS;
        }
    }

    @Override
    public String toString() {
        return descricao + " (Desconto: " + percentualDesconto + "%)";
    }
}