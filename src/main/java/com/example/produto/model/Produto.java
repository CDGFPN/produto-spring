package com.example.produto.model;

public class Produto {
    private int id;
    private String nome;
    private boolean disponivel;
    private double preco;
    private Categoria categoria;
    private Fornecedor fornecedor; // Novo relacionamento
    private int quantidadeEstoque;

    // Construtor padrão
    public Produto() {
        this.disponivel = true;
        this.quantidadeEstoque = 0;
    }

    // Construtor com parâmetros básicos
    public Produto(int id, String nome, double preco) {
        this();
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // Construtor completo
    public Produto(int id, String nome, double preco, Categoria categoria, Fornecedor fornecedor) {
        this(id, nome, preco); // Chama o construtor anterior
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    // Sobrecarga de método: exibir resumo simples
    public void exibirResumo() {
        exibirResumo(false);
    }

    // Sobrecarga de método: exibir resumo com detalhes
    public void exibirResumo(boolean mostrarDetalhes) {
        double precoComDesconto = calcularDesconto();
        System.out.println("\n--- Resumo do Produto ---");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Categoria: " + (categoria != null ? categoria.getDescricao() : "Sem categoria"));
        System.out.println("Preco original: R$ " + String.format("%.2f", preco));
        System.out.println("Preco com desconto: R$ " + String.format("%.2f", precoComDesconto));
        System.out.println("Disponivel: " + (disponivel ? "Sim" : "Nao"));
        System.out.println("Estoque: " + quantidadeEstoque + " unidades");

        if (mostrarDetalhes && fornecedor != null) {
            System.out.println("\n--- Informacoes do Fornecedor ---");
            System.out.println(fornecedor.toString());
        }

        System.out.println("Classificacao: " + classificarPorPreco());

        // Usando operadores logicos para emitir alertas
        if (!disponivel && preco > 100) {
            System.out.println("ALERTA: Produto caro indisponivel no momento!");
        }

        if (quantidadeEstoque < 5 && disponivel) {
            System.out.println("AVISO: Estoque baixo!");
        }

        System.out.println("-------------------------");
    }

    // Método auxiliar para classificação
    private String classificarPorPreco() {
        if (preco < 50) {
            return "Produto Economico";
        } else if (preco >= 50 && preco < 200) {
            return "Produto de Preco Medio";
        } else if (preco >= 200 && preco < 500) {
            return "Produto Premium";
        } else {
            return "Produto de Luxo";
        }
    }

    // Método para calcular desconto baseado na categoria
    private double calcularDesconto() {
        if (categoria == null) {
            return preco * 0.92; // 8% de desconto padrão
        }

        double percentualDesconto;
        switch (categoria) {
            case ELETRONICOS:
                percentualDesconto = 0.15; // 15%
                break;
            case ALIMENTOS:
                percentualDesconto = 0.05; // 5%
                break;
            case VESTUARIO:
                percentualDesconto = 0.20; // 20%
                break;
            case LIVROS:
                percentualDesconto = 0.10; // 10%
                break;
            default:
                percentualDesconto = 0.08; // 8%
                break;
        }

        return preco * (1 - percentualDesconto);
    }

    // Sobrecarga: adicionar estoque sem parâmetro (adiciona 1)
    public void adicionarEstoque() {
        adicionarEstoque(1);
    }

    // Sobrecarga: adicionar estoque com quantidade específica
    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
            System.out.println(quantidade + " unidade(s) adicionada(s). Estoque atual: " + this.quantidadeEstoque);
        } else {
            System.out.println("Quantidade invalida!");
        }
    }

    // Método para remover estoque
    public boolean removerEstoque(int quantidade) {
        if (quantidade <= 0) {
            System.out.println("Quantidade invalida!");
            return false;
        }

        if (quantidade > this.quantidadeEstoque) {
            System.out.println("Estoque insuficiente! Disponivel: " + this.quantidadeEstoque);
            return false;
        }

        this.quantidadeEstoque -= quantidade;
        System.out.println(quantidade + " unidade(s) removida(s). Estoque atual: " + this.quantidadeEstoque);

        // Atualiza disponibilidade se estoque zerar
        if (this.quantidadeEstoque == 0) {
            this.disponivel = false;
            System.out.println("Produto ficou indisponivel (estoque zerado).");
        }

        return true;
    }

    // Método que utiliza método de classe padrão do Java (String)
    public boolean contemNome(String termo) {
        if (this.nome == null || termo == null) {
            return false;
        }
        return this.nome.toLowerCase().contains(termo.toLowerCase().trim());
    }

    // Implementação do toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Produto{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", preco=R$").append(String.format("%.2f", preco));
        sb.append(", categoria=").append(categoria != null ? categoria : "N/A");
        sb.append(", disponivel=").append(disponivel);
        sb.append(", estoque=").append(quantidadeEstoque);
        sb.append(", fornecedor=").append(fornecedor != null ? fornecedor.getNome() : "N/A");
        sb.append('}');
        return sb.toString();
    }

    // Getters com validação
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

    // Setters com validação (Encapsulamento)
    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            System.out.println("ID invalido! Deve ser maior que 0.");
        }
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome.trim();
        } else {
            System.out.println("Nome invalido!");
        }
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setPreco(double preco) {
        if (preco > 0 && preco <= 100000) {
            this.preco = preco;
        } else {
            System.out.println("Preco invalido! Deve estar entre R$ 0,01 e R$ 100.000,00");
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
            // Atualiza disponibilidade automaticamente
            if (quantidadeEstoque == 0) {
                this.disponivel = false;
            }
        } else {
            System.out.println("Quantidade de estoque invalida!");
        }
    }
}