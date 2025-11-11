package com.example.produto.model;

public class Produto {
    private int id;
    private String nome;
    private boolean disponivel;
    private double preco;
    private String categoria;

    public Produto() {}

    public void exibirResumo() {
        double precoComDesconto = calcularDesconto();
        System.out.println("\n--- Resumo do Produto ---");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Categoria: " + categoria);
        System.out.println("Preco original: R$ " + String.format("%.2f", preco));
        System.out.println("Preco com desconto: R$ " + String.format("%.2f", precoComDesconto));
        System.out.println("Disponivel: " + (disponivel ? "Sim" : "Nao"));

        // Usando if-else if-else para classificar o produto por preco
        if (preco < 50) {
            System.out.println("Classificacao: Produto Economico");
        } else if (preco >= 50 && preco < 200) {
            System.out.println("Classificacao: Produto de Preco Medio");
        } else if (preco >= 200 && preco < 500) {
            System.out.println("Classificacao: Produto Premium");
        } else {
            System.out.println("Classificacao: Produto de Luxo");
        }

        // Usando operadores logicos para emitir alertas
        if (!disponivel && preco > 100) {
            System.out.println("ALERTA: Produto caro indisponivel no momento!");
        }

        System.out.println("-------------------------");
    }

    private double calcularDesconto() {
        // Usando switch-case para aplicar desconto baseado na categoria
        double percentualDesconto = 0.10; // padrao 10%

        if (categoria != null) {
            switch (categoria.toLowerCase()) {
                case "eletronicos":
                    percentualDesconto = 0.15; // 15% para eletronicos
                    break;
                case "alimentos":
                    percentualDesconto = 0.05; // 5% para alimentos
                    break;
                case "vestuario":
                    percentualDesconto = 0.20; // 20% para vestuario
                    break;
                case "livros":
                    percentualDesconto = 0.10; // 10% para livros
                    break;
                default:
                    percentualDesconto = 0.08; // 8% para outras categorias
                    break;
            }
        }

        return preco * (1 - percentualDesconto);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}