package com.example.produto.model;

public class ProdutoFisico extends Produto implements Descritivel {
    private double peso;
    private double comprimento;
    private double largura;
    private double altura;

    public ProdutoFisico() {
        super();
    }

    public ProdutoFisico(int id, String nome, double preco, Categoria categoria,
                         Fornecedor fornecedor, double peso, double comprimento,
                         double largura, double altura) {
        super(id, nome, preco, categoria, fornecedor);
        this.peso = peso;
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public String obterTipoProduto() {
        return "Produto Físico";
    }

    @Override
    public String obterDescricaoCurta() {
        return getNome() + " (" + String.format("%.2f", peso) + " kg)";
    }

    @Override
    public String obterDescricaoCompleta() {
        return getNome() + " - Peso: " + String.format("%.2f", peso) + " kg, " +
                "Dimensões: " + String.format("%.1f", comprimento) + "x" +
                String.format("%.1f", largura) + "x" +
                String.format("%.1f", altura) + " cm, " +
                "Volume: " + String.format("%.2f", calcularVolume()) + " cm³";
    }

    public double calcularVolume() {
        return comprimento * largura * altura;
    }

    public double calcularCustoFrete() {
        double volumeMetrico = calcularVolume() / 1000000;
        double custoBase = peso * 5.0;
        double custoVolume = volumeMetrico * 100.0;
        return Math.max(custoBase, custoVolume);
    }

    @Override
    public String toString() {
        return "ProdutoFisico{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", peso=" + String.format("%.2f", peso) + "kg" +
                ", dimensoes=" + String.format("%.1fx%.1fx%.1f", comprimento, largura, altura) + "cm" +
                ", preco=R$" + String.format("%.2f", getPreco()) +
                '}';
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso > 0) {
            this.peso = peso;
        }
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        if (comprimento > 0) {
            this.comprimento = comprimento;
        }
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        if (largura > 0) {
            this.largura = largura;
        }
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        if (altura > 0) {
            this.altura = altura;
        }
    }
}