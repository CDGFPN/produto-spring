package com.example.produto.model;

public class FornecedorNacional extends Fornecedor implements Avaliavel {
    private String estado;
    private double somaAvaliacoes;
    private int totalAvaliacoes;

    public FornecedorNacional() {
        super();
        this.somaAvaliacoes = 0;
        this.totalAvaliacoes = 0;
    }

    public FornecedorNacional(int id, String nome, String cnpj, String telefone,
                              String email, String estado) {
        super(id, nome, cnpj, telefone, email);
        this.estado = estado;
        this.somaAvaliacoes = 0;
        this.totalAvaliacoes = 0;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Tipo: Nacional");
        System.out.println("Estado: " + (estado != null ? estado : "Não informado"));
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

    public boolean isDoEstado(String uf) {
        return this.estado != null && this.estado.equalsIgnoreCase(uf);
    }

    @Override
    public String toString() {
        return "FornecedorNacional{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", estado='" + estado + '\'' +
                ", avaliacao=" + String.format("%.1f", obterAvaliacaoMedia()) +
                '}';
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}