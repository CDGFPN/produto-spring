package com.example.produto;

import com.example.produto.model.Produto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ProdutoApplication {

    // Array para armazenar produtos (capacidade maxima de 100 produtos)
    private static Produto[] produtos = new Produto[100];
    private static int totalProdutos = 0;
    private static int contadorId = 1;

    public static void main(String[] args) {
        SpringApplication.run(ProdutoApplication.class, args);

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  Sistema de Cadastro de Produtos v2.0");
        System.out.println("========================================");

        // Loop principal usando do-while (garante execucao minima de 1 vez)
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer

            // Switch-case para navegar entre as opcoes do menu
            switch (opcao) {
                case 1:
                    cadastrarProduto(scanner);
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    buscarProdutoPorNome(scanner);
                    break;
                case 4:
                    filtrarPorPreco(scanner);
                    break;
                case 5:
                    exibirRelatorio();
                    break;
                case 6:
                    atualizarDisponibilidade(scanner);
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema... Ate logo!");
                    break;
                default:
                    System.out.println("\nOpcao invalida! Por favor, tente novamente.");
                    break;
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1 - Cadastrar novo produto");
        System.out.println("2 - Listar todos os produtos");
        System.out.println("3 - Buscar produto por nome");
        System.out.println("4 - Filtrar produtos por faixa de preco");
        System.out.println("5 - Exibir relatorio estatistico");
        System.out.println("6 - Atualizar disponibilidade");
        System.out.println("0 - Sair");
        System.out.println("====================================");
        System.out.print("Escolha uma opcao: ");
    }

    private static void cadastrarProduto(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE PRODUTO ===");

        // Verifica se ainda ha espaco no array
        if (totalProdutos >= produtos.length) {
            System.out.println("Limite de produtos atingido! Nao e possivel cadastrar mais produtos.");
            return;
        }

        Produto produto = new Produto();
        produto.setId(contadorId++);

        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        // Validacao usando if e operador logico ||
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome invalido! Cadastro cancelado.");
            return;
        }
        produto.setNome(nome);

        System.out.print("Digite o preco do produto: R$ ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        // Validacao com operadores logicos && e ||
        if (preco <= 0 || preco > 100000) {
            System.out.println("Preco invalido! Deve ser entre R$ 0,01 e R$ 100.000,00");
            System.out.println("Cadastro cancelado.");
            return;
        }
        produto.setPreco(preco);

        System.out.println("\nCategorias disponiveis:");
        System.out.println("1 - Eletronicos");
        System.out.println("2 - Alimentos");
        System.out.println("3 - Vestuario");
        System.out.println("4 - Livros");
        System.out.println("5 - Outros");
        System.out.print("Escolha a categoria (1-5): ");
        int categoriaOpcao = scanner.nextInt();
        scanner.nextLine();

        // Switch-case para definir a categoria
        switch (categoriaOpcao) {
            case 1:
                produto.setCategoria("Eletronicos");
                break;
            case 2:
                produto.setCategoria("Alimentos");
                break;
            case 3:
                produto.setCategoria("Vestuario");
                break;
            case 4:
                produto.setCategoria("Livros");
                break;
            case 5:
                produto.setCategoria("Outros");
                break;
            default:
                produto.setCategoria("Outros");
                System.out.println("Categoria invalida, definida como 'Outros'");
                break;
        }

        System.out.print("O produto esta disponivel? (true/false): ");
        produto.setDisponivel(scanner.nextBoolean());
        scanner.nextLine();

        produtos[totalProdutos] = produto;
        totalProdutos++;

        System.out.println("\nProduto cadastrado com sucesso!");
        produto.exibirResumo();
    }

    private static void listarProdutos() {
        System.out.println("\n=== LISTA DE PRODUTOS ===");

        // if-else para verificar se existem produtos cadastrados
        if (totalProdutos == 0) {
            System.out.println("Nenhum produto cadastrado ainda.");
            return;
        }

        // Loop for para iterar sobre o array de produtos
        for (int i = 0; i < totalProdutos; i++) {
            produtos[i].exibirResumo();
        }

        System.out.println("\nTotal de produtos cadastrados: " + totalProdutos);
    }

    private static void buscarProdutoPorNome(Scanner scanner) {
        System.out.println("\n=== BUSCAR PRODUTO ===");
        System.out.print("Digite o nome do produto (ou parte dele): ");
        String termoBusca = scanner.nextLine().toLowerCase();

        boolean encontrou = false;
        int contador = 0;

        // Loop for para buscar produtos
        for (int i = 0; i < totalProdutos; i++) {
            Produto p = produtos[i];

            // Operador && para verificar multiplas condicoes
            if (p.getNome() != null && p.getNome().toLowerCase().contains(termoBusca)) {
                // Usando operador ! (NOT) para verificar primeira ocorrencia
                if (!encontrou) {
                    System.out.println("\nProdutos encontrados:");
                    encontrou = true;
                }
                p.exibirResumo();
                contador++;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum produto encontrado com esse nome.");
        } else {
            System.out.println("\nTotal de produtos encontrados: " + contador);
        }
    }

    private static void filtrarPorPreco(Scanner scanner) {
        System.out.println("\n=== FILTRAR POR PRECO ===");
        System.out.print("Digite o preco minimo: R$ ");
        double precoMin = scanner.nextDouble();
        System.out.print("Digite o preco maximo: R$ ");
        double precoMax = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("\nProdutos na faixa de R$ " + String.format("%.2f", precoMin) +
                " a R$ " + String.format("%.2f", precoMax) + ":");

        int contador = 0;
        int indice = 0;

        // Loop while para percorrer os produtos
        while (indice < totalProdutos) {
            Produto p = produtos[indice];

            // Operadores logicos && com >= e <= para verificar faixa de preco
            if (p.getPreco() >= precoMin && p.getPreco() <= precoMax) {
                p.exibirResumo();
                contador++;
            }
            indice++;
        }

        if (contador == 0) {
            System.out.println("Nenhum produto encontrado nessa faixa de preco.");
        } else {
            System.out.println("\nTotal de produtos encontrados: " + contador);
        }
    }

    private static void exibirRelatorio() {
        System.out.println("\n=== RELATORIO ESTATISTICO ===");

        if (totalProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        double somaPrecos = 0;
        double maiorPreco = produtos[0].getPreco();
        double menorPreco = produtos[0].getPreco();
        int disponiveis = 0;
        int indisponiveis = 0;

        // Loop for para calcular estatisticas
        for (int i = 0; i < totalProdutos; i++) {
            Produto p = produtos[i];
            double preco = p.getPreco();

            somaPrecos += preco;

            // if-else para encontrar maior e menor preco
            if (preco > maiorPreco) {
                maiorPreco = preco;
            }

            if (preco < menorPreco) {
                menorPreco = preco;
            }

            // Contabiliza produtos disponiveis e indisponiveis
            if (p.isDisponivel()) {
                disponiveis++;
            } else {
                indisponiveis++;
            }
        }

        double mediaPrecos = somaPrecos / totalProdutos;

        System.out.println("Total de produtos: " + totalProdutos);
        System.out.println("Preco medio: R$ " + String.format("%.2f", mediaPrecos));
        System.out.println("Maior preco: R$ " + String.format("%.2f", maiorPreco));
        System.out.println("Menor preco: R$ " + String.format("%.2f", menorPreco));
        System.out.println("Produtos disponiveis: " + disponiveis);
        System.out.println("Produtos indisponiveis: " + indisponiveis);

        // Analise usando if-else if-else
        System.out.println("\n--- Analise do Catalogo ---");
        if (mediaPrecos < 100) {
            System.out.println("Catalogo com precos acessiveis");
        } else if (mediaPrecos >= 100 && mediaPrecos < 500) {
            System.out.println("Catalogo de preco medio");
        } else {
            System.out.println("Catalogo premium com produtos de alto valor");
        }

        // Usando operadores logicos para analise adicional
        if (disponiveis > 0 && indisponiveis == 0) {
            System.out.println("Excelente! Todos os produtos estao disponiveis.");
        } else if (indisponiveis > disponiveis) {
            System.out.println("Atencao: Muitos produtos indisponiveis no momento.");
        }
    }

    private static void atualizarDisponibilidade(Scanner scanner) {
        System.out.println("\n=== ATUALIZAR DISPONIBILIDADE ===");

        if (totalProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean encontrou = false;

        // Loop for com break para sair quando encontrar o produto
        for (int i = 0; i < totalProdutos; i++) {
            Produto p = produtos[i];

            if (p.getId() == id) {
                encontrou = true;
                System.out.println("\nProduto encontrado:");
                p.exibirResumo();

                System.out.print("\nNovo status de disponibilidade (true/false): ");
                boolean novoStatus = scanner.nextBoolean();
                scanner.nextLine();

                p.setDisponivel(novoStatus);
                System.out.println("\nDisponibilidade atualizada com sucesso!");
                p.exibirResumo();

                break; // Usa break para sair do loop apos encontrar e atualizar
            }
        }

        // Operador ! (NOT) para verificar se nao encontrou
        if (!encontrou) {
            System.out.println("Produto com ID " + id + " nao foi encontrado.");
        }
    }
}