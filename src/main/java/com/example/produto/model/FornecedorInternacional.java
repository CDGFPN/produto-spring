package com.example.produto.model;

public class FornecedorInternacional extends Fornecedor implements Avaliavel {
    private String pais;
    private double taxaImportacao;
    private double somaAvaliacoes;
    private int totalAvaliacoes;

    public FornecedorInternacional() {
        super();
        this.somaAvaliacoes = 0;
        this.totalAvaliacoes = 0;
    }

    public FornecedorInternacional(int id, String nome, String cnpj, String telefone,
                                   String email, String pais, double taxaImportacao) {
        super(id, nome, cnpj, telefone, email);
        this.pais = pais;
        this.taxaImportacao = taxaImportacao;
        this.somaAvaliacoes = 0;
        this.totalAvaliacoes = 0;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo: Internacional");
        System.out.println("País: " + (pais != null ? pais : "Não informado"));
        System.out.println("Taxa de importação: " + String.format("%.2f", taxaImportacao) + "%");
        System.out.println("Avaliação: " + String.format("%.1f", obterAvaliacaoMedia()) +
                " (" + totalAvaliacoes + " avaliações)");
    }

    @Override
    public void avaliar(double nota) {
        if (nota >= 0 && nota <= 5) {
            somaAvaliacoes += nota;
            totalAvaliacoes++;
            System.out.println("Avaliação registrada: " + nota + " estrelas");
        } else {
            System.out.println("Nota inválida! Deve estar entre 0 e 5.");
        }
    }

    @Override
    public double obterAvaliacaoMedia() {
        if (totalAvaliacoes == 0) return 0.0;
        return somaAvaliacoes / totalAvaliacoes;
    }

    @Override
    public int obterTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public double calcularCustoComImportacao(double valorProduto) {
        return valorProduto * (1 + taxaImportacao / 100);
    }

    @Override
    public boolean validarCNPJ() {
        if (getCnpj() == null) {
            return false;
        }
        String cnpjLimpo = getCnpj().replaceAll("[^0-9A-Za-z]", "");
        return cnpjLimpo.length() >= 8;
    }

    @Override
    public String toString() {
        return "FornecedorInternacional{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", pais='" + pais + '\'' +
                ", taxaImportacao=" + String.format("%.2f", taxaImportacao) + "%" +
                ", avaliacao=" + String.format("%.1f", obterAvaliacaoMedia()) +
                '}';
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getTaxaImportacao() {
        return taxaImportacao;
    }

    public void setTaxaImportacao(double taxaImportacao) {
        if (taxaImportacao >= 0) {
            this.taxaImportacao = taxaImportacao;
        }
    }
}