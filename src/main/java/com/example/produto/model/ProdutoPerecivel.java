package com.example.produto.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ProdutoPerecivel extends Produto implements Descritivel {
    private String dataValidade;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ProdutoPerecivel() {
        super();
    }

    public ProdutoPerecivel(int id, String nome, double preco, Categoria categoria,
                            Fornecedor fornecedor, String dataValidade) {
        super(id, nome, preco, categoria, fornecedor);
        this.dataValidade = dataValidade;
    }

    @Override
    public String obterTipoProduto() {
        return "Produto Perecível";
    }

    @Override
    public String obterDescricaoCurta() {
        return getNome() + " (Val: " + dataValidade + ")";
    }

    @Override
    public String obterDescricaoCompleta() {
        long diasParaVencer = calcularDiasParaVencer();
        String status = diasParaVencer < 0 ? "VENCIDO" :
                diasParaVencer <= 7 ? "VENCE EM BREVE" : "DENTRO DA VALIDADE";

        return getNome() + " - Validade: " + dataValidade +
                " (" + Math.abs(diasParaVencer) + " dias), Status: " + status;
    }

    public long calcularDiasParaVencer() {
        try {
            LocalDate hoje = LocalDate.now();
            LocalDate validade = LocalDate.parse(dataValidade, FORMATTER);
            return ChronoUnit.DAYS.between(hoje, validade);
        } catch (Exception e) {
            return -999;
        }
    }

    public boolean estaVencido() {
        return calcularDiasParaVencer() < 0;
    }

    @Override
    public void exibirResumo(boolean mostrarDetalhes) {
        super.exibirResumo(mostrarDetalhes);

        long dias = calcularDiasParaVencer();
        if (dias < 0) {
            System.out.println("*** ATENÇÃO: PRODUTO VENCIDO! ***");
        } else if (dias <= 7) {
            System.out.println("*** ATENÇÃO: VENCE EM " + dias + " DIAS! ***");
        }
    }

    @Override
    public String toString() {
        return "ProdutoPerecivel{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", dataValidade='" + dataValidade + '\'' +
                ", diasParaVencer=" + calcularDiasParaVencer() +
                ", preco=R$" + String.format("%.2f", getPreco()) +
                '}';
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }
}