package com.example.produto.model;

public class ProdutoDigital extends Produto implements Descritivel {
    private String formato;
    private double tamanhoMB;

    public ProdutoDigital() {
        super();
    }

    public ProdutoDigital(int id, String nome, double preco, Categoria categoria,
                          Fornecedor fornecedor, String formato, double tamanhoMB) {
        super(id, nome, preco, categoria, fornecedor);
        this.formato = formato;
        this.tamanhoMB = tamanhoMB;
    }

    @Override
    public String obterTipoProduto() {
        return "Produto Digital";
    }

    @Override
    public String obterDescricaoCurta() {
        return getNome() + " (" + formato + ")";
    }

    @Override
    public String obterDescricaoCompleta() {
        return getNome() + " - Formato: " + formato +
                ", Tamanho: " + String.format("%.2f", tamanhoMB) + " MB, " +
                "Preço: R$ " + String.format("%.2f", getPreco());
    }

    @Override
    public String toString() {
        return "ProdutoDigital{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", formato='" + formato + '\'' +
                ", tamanhoMB=" + String.format("%.2f", tamanhoMB) +
                ", preco=R$" + String.format("%.2f", getPreco()) +
                '}';
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public double getTamanhoMB() {
        return tamanhoMB;
    }

    public void setTamanhoMB(double tamanhoMB) {
        if (tamanhoMB > 0) {
            this.tamanhoMB = tamanhoMB;
        }
    }
}