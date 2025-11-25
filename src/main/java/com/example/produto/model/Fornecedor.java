package com.example.produto.model;

public class Fornecedor {
    private int id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private boolean ativo;
    private int quantidadeProdutosFornecidos;

    // Construtor padrão
    public Fornecedor() {
        this.ativo = true;
        this.quantidadeProdutosFornecidos = 0;
    }

    // Construtor com parâmetros básicos
    public Fornecedor(int id, String nome, String cnpj) {
        this();
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    // Construtor completo
    public Fornecedor(int id, String nome, String cnpj, String telefone, String email) {
        this(id, nome, cnpj); // Chama o construtor anterior
        this.telefone = telefone;
        this.email = email;
    }

    // Método para exibir informações do fornecedor
    public void exibirInformacoes() {
        System.out.println("\n=== Informacoes do Fornecedor ===");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("CNPJ: " + formatarCNPJ());
        System.out.println("Telefone: " + (telefone != null ? telefone : "Nao informado"));
        System.out.println("Email: " + (email != null ? email : "Nao informado"));
        System.out.println("Status: " + (ativo ? "Ativo" : "Inativo"));
        System.out.println("Produtos fornecidos: " + quantidadeProdutosFornecidos);
        System.out.println("================================");
    }

    // Método que utiliza String (classe padrão do Java)
    private String formatarCNPJ() {
        if (cnpj == null || cnpj.length() != 14) {
            return cnpj;
        }
        // Formata: 00.000.000/0000-00
        return cnpj.substring(0, 2) + "." +
                cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" +
                cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14);
    }

    // Sobrecarga: adicionar produto sem quantidade
    public void adicionarProdutoFornecido() {
        adicionarProdutoFornecido(1);
    }

    // Sobrecarga: adicionar produto com quantidade
    public void adicionarProdutoFornecido(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeProdutosFornecidos += quantidade;
        }
    }

    // Método para validar CNPJ (simplificado)
    public boolean validarCNPJ() {
        if (cnpj == null) {
            return false;
        }
        // Remove caracteres não numéricos
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
        return cnpjLimpo.length() == 14;
    }

    // Método para validar email (simplificado)
    public boolean validarEmail() {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    // Implementação do toString()
    @Override
    public String toString() {
        return "Fornecedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + formatarCNPJ() + '\'' +
                ", telefone='" + (telefone != null ? telefone : "N/A") + '\'' +
                ", ativo=" + ativo +
                ", produtosFornecidos=" + quantidadeProdutosFornecidos +
                '}';
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getQuantidadeProdutosFornecidos() {
        return quantidadeProdutosFornecidos;
    }

    // Setters com validação (Encapsulamento)
    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            System.out.println("ID invalido!");
        }
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome.trim();
        } else {
            System.out.println("Nome invalido!");
        }
    }

    public void setCnpj(String cnpj) {
        if (cnpj != null) {
            // Remove caracteres não numéricos
            String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");
            if (cnpjLimpo.length() == 14) {
                this.cnpj = cnpjLimpo;
            } else {
                System.out.println("CNPJ invalido! Deve conter 14 digitos.");
            }
        }
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email.trim();
        } else {
            System.out.println("Email invalido!");
        }
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setQuantidadeProdutosFornecidos(int quantidadeProdutosFornecidos) {
        if (quantidadeProdutosFornecidos >= 0) {
            this.quantidadeProdutosFornecidos = quantidadeProdutosFornecidos;
        }
    }
}