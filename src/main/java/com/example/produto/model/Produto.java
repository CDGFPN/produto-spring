package com.example.produto.model;

public class Produto {
    private int id;
    private String nome;
    private boolean disponivel;
    private double preco;

    public Produto() {}

    public void exibirResumo() {
        double precoComDesconto = calcularDesconto();
        System.out.println("\n📦 Produto: " + nome);
        System.out.println("💰 Preço original: R$ " + preco);
        System.out.println("🏷️ Preço com desconto: R$ " + precoComDesconto);
        System.out.println("📦 Disponível: " + (disponivel ? "Sim" : "Não"));
    }

    private double calcularDesconto() {
        return preco * 0.9; // aplica 10% de desconto
    }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    public void setPreco(double preco) { this.preco = preco; }
}
