package com.example.produto.model;

// Classe ABSTRATA - não pode ser instanciada diretamente
public abstract class Produto {
    private int id;
    private String nome;
    private boolean disponivel;
    private double preco;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private int quantidadeEstoque;

    public Produto() {
        this.disponivel = true;
        this.quantidadeEstoque = 0;
    }

    public Produto(int id, String nome, double preco) {
        this();
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Produto(int id, String nome, double preco, Categoria categoria, Fornecedor fornecedor) {
        this(id, nome, preco);
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    // MÉTODO ABSTRATO - subclasses devem implementar
    public abstract String obterTipoProduto();

    public void exibirResumo() {
        exibirResumo(false);
    }

    public void exibirResumo(boolean mostrarDetalhes) {
        double precoComDesconto = calcularDesconto();
        System.out.println("\n--- Resumo do Produto ---");
        System.out.println("Tipo: " + obterTipoProduto());
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Categoria: " + (categoria != null ? categoria.getDescricao() : "Sem categoria"));
        System.out.println("Preço original: R$ " + String.format("%.2f", preco));
        System.out.println("Preço com desconto: R$ " + String.format("%.2f", precoComDesconto));
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
        System.out.println("Estoque: " + quantidadeEstoque + " unidades");

        if (mostrarDetalhes && fornecedor != null) {
            System.out.println("\n--- Informações do Fornecedor ---");
            System.out.println(fornecedor.toString());
        }

        System.out.println("Classificação: " + classificarPorPreco());

        if (!disponivel && preco > 100) {
            System.out.println("ALERTA: Produto caro indisponível no momento!");
        }

        if (quantidadeEstoque < 5 && disponivel) {
            System.out.println("AVISO: Estoque baixo!");
        }

        System.out.println("-------------------------");
    }

    private String classificarPorPreco() {
        if (preco < 50) {
            return "Produto Econômico";
        } else if (preco >= 50 && preco < 200) {
            return "Produto de Preço Médio";
        } else if (preco >= 200 && preco < 500) {
            return "Produto Premium";
        } else {
            return "Produto de Luxo";
        }
    }

    // Método FINAL - não pode ser sobrescrito
    public final double calcularDesconto() {
        if (categoria == null) {
            return preco * 0.92;
        }

        double percentualDesconto;
        switch (categoria) {
            case ELETRONICOS:
                percentualDesconto = 0.15;
                break;
            case ALIMENTOS:
                percentualDesconto = 0.05;
                break;
            case VESTUARIO:
                percentualDesconto = 0.20;
                break;
            case LIVROS:
                percentualDesconto = 0.10;
                break;
            default:
                percentualDesconto = 0.08;
                break;
        }

        return preco * (1 - percentualDesconto);
    }

    public void adicionarEstoque() {
        adicionarEstoque(1);
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
            System.out.println(quantidade + " unidade(s) adicionada(s). Estoque atual: " + this.quantidadeEstoque);
        } else {
            System.out.println("Quantidade inválida!");
        }
    }

    public boolean removerEstoque(int quantidade) {
        if (quantidade <= 0) {
            System.out.println("Quantidade inválida!");
            return false;
        }

        if (quantidade > this.quantidadeEstoque) {
            System.out.println("Estoque insuficiente! Disponível: " + this.quantidadeEstoque);
            return false;
        }

        this.quantidadeEstoque -= quantidade;
        System.out.println(quantidade + " unidade(s) removida(s). Estoque atual: " + this.quantidadeEstoque);

        if (this.quantidadeEstoque == 0) {
            this.disponivel = false;
            System.out.println("Produto ficou indisponível (estoque zerado).");
        }

        return true;
    }

    public boolean contemNome(String termo) {
        if (this.nome == null || termo == null) {
            return false;
        }
        return this.nome.toLowerCase().contains(termo.toLowerCase().trim());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto{");
        sb.append("tipo='").append(obterTipoProduto()).append('\'');
        sb.append(", id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", preco=R$").append(String.format("%.2f", preco));
        sb.append(", categoria=").append(categoria != null ? categoria : "N/A");
        sb.append(", disponivel=").append(disponivel);
        sb.append(", estoque=").append(quantidadeEstoque);
        sb.append(", fornecedor=").append(fornecedor != null ? fornecedor.getNome() : "N/A");
        sb.append('}');
        return sb.toString();
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

    public Categoria getCategoria() {
        return categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    // Setters
    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            System.out.println("ID inválido! Deve ser maior que 0.");
        }
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome.trim();
        } else {
            System.out.println("Nome inválido!");
        }
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setPreco(double preco) {
        if (preco > 0 && preco <= 100000) {
            this.preco = preco;
        } else {
            System.out.println("Preço inválido! Deve estar entre R$ 0,01 e R$ 100.000,00");
        }
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque >= 0) {
            this.quantidadeEstoque = quantidadeEstoque;
            if (quantidadeEstoque == 0) {
                this.disponivel = false;
            }
        } else {
            System.out.println("Quantidade de estoque inválida!");
        }
    }
}