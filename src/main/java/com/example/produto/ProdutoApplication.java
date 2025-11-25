package com.example.produto;

import com.example.produto.model.Categoria;
import com.example.produto.model.Fornecedor;
import com.example.produto.model.Produto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ProdutoApplication {

    // Arrays para armazenar produtos e fornecedores
    private static Produto[] produtos = new Produto[100];
    private static Fornecedor[] fornecedores = new Fornecedor[50];
    private static int totalProdutos = 0;
    private static int totalFornecedores = 0;
    private static int contadorIdProduto = 1;
    private static int contadorIdFornecedor = 1;

    public static void main(String[] args) {
        SpringApplication.run(ProdutoApplication.class, args);

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  Sistema de Cadastro de Produtos v3.0");
        System.out.println("  Com Gestao de Fornecedores");
        System.out.println("========================================");

        // Demonstração dos construtores e métodos
        demonstrarPOO();

        // Loop principal
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

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
                case 7:
                    gerenciarEstoque(scanner);
                    break;
                case 8:
                    cadastrarFornecedor(scanner);
                    break;
                case 9:
                    listarFornecedores();
                    break;
                case 10:
                    associarFornecedor(scanner);
                    break;
                case 11:
                    exibirProdutoDetalhado(scanner);
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
        System.out.println("=== PRODUTOS ===");
        System.out.println("1 - Cadastrar novo produto");
        System.out.println("2 - Listar todos os produtos");
        System.out.println("3 - Buscar produto por nome");
        System.out.println("4 - Filtrar produtos por faixa de preco");
        System.out.println("5 - Exibir relatorio estatistico");
        System.out.println("6 - Atualizar disponibilidade");
        System.out.println("7 - Gerenciar estoque");
        System.out.println("11 - Exibir produto detalhado");
        System.out.println("\n=== FORNECEDORES ===");
        System.out.println("8 - Cadastrar fornecedor");
        System.out.println("9 - Listar fornecedores");
        System.out.println("10 - Associar fornecedor a produto");
        System.out.println("\n0 - Sair");
        System.out.println("====================================");
        System.out.print("Escolha uma opcao: ");
    }

    // Método para demonstrar diferentes construtores e métodos
    private static void demonstrarPOO() {
        System.out.println("\n=== DEMONSTRACAO POO ===");

        // Testando diferentes construtores de Fornecedor
        Fornecedor f1 = new Fornecedor(); // Construtor padrão
        f1.setId(999);
        f1.setNome("Fornecedor Teste 1");
        f1.setCnpj("12345678901234");

        Fornecedor f2 = new Fornecedor(998, "Fornecedor Teste 2", "98765432109876"); // Construtor com 3 parâmetros

        Fornecedor f3 = new Fornecedor(997, "Fornecedor Teste 3", "11111111111111",
                "(41) 3333-4444", "contato@fornecedor3.com"); // Construtor completo

        // Testando diferentes construtores de Produto
        Produto p1 = new Produto(); // Construtor padrão
        p1.setId(999);
        p1.setNome("Produto Teste 1");
        p1.setPreco(99.90);

        Produto p2 = new Produto(998, "Produto Teste 2", 149.90); // Construtor com 3 parâmetros

        Produto p3 = new Produto(997, "Produto Teste 3", 199.90, Categoria.ELETRONICOS, f3); // Construtor completo

        // Testando sobrecarga de métodos
        System.out.println("\n--- Testando sobrecarga de metodos ---");
        p3.adicionarEstoque(); // Adiciona 1
        p3.adicionarEstoque(10); // Adiciona 10

        // Testando toString()
        System.out.println("\n--- Testando toString() ---");
        System.out.println(f3.toString());
        System.out.println(p3.toString());

        System.out.println("\n=== FIM DA DEMONSTRACAO ===\n");
    }

    private static void cadastrarProduto(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE PRODUTO ===");

        if (totalProdutos >= produtos.length) {
            System.out.println("Limite de produtos atingido!");
            return;
        }

        // Usando construtor com parâmetros
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome invalido! Cadastro cancelado.");
            return;
        }

        System.out.print("Digite o preco do produto: R$ ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        if (preco <= 0 || preco > 100000) {
            System.out.println("Preco invalido! Cadastro cancelado.");
            return;
        }

        // Criando produto com construtor de 3 parâmetros
        Produto produto = new Produto(contadorIdProduto++, nome, preco);

        // Definindo categoria usando enum
        System.out.println("\nCategorias disponiveis:");
        for (Categoria cat : Categoria.values()) {
            System.out.println((cat.ordinal() + 1) + " - " + cat.getDescricao());
        }
        System.out.print("Escolha a categoria (1-5): ");
        int categoriaOpcao = scanner.nextInt();
        scanner.nextLine();

        produto.setCategoria(Categoria.porOpcao(categoriaOpcao));

        System.out.print("Quantidade inicial em estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();
        produto.setQuantidadeEstoque(estoque);

        System.out.print("O produto esta disponivel? (true/false): ");
        produto.setDisponivel(scanner.nextBoolean());
        scanner.nextLine();

        // Opção de associar fornecedor
        if (totalFornecedores > 0) {
            System.out.print("\nDeseja associar um fornecedor? (s/n): ");
            String resp = scanner.nextLine();
            if (resp.equalsIgnoreCase("s")) {
                listarFornecedores();
                System.out.print("Digite o ID do fornecedor: ");
                int idFornecedor = scanner.nextInt();
                scanner.nextLine();

                Fornecedor fornecedor = buscarFornecedorPorId(idFornecedor);
                if (fornecedor != null) {
                    produto.setFornecedor(fornecedor);
                    fornecedor.adicionarProdutoFornecido();
                    System.out.println("Fornecedor associado com sucesso!");
                }
            }
        }

        produtos[totalProdutos] = produto;
        totalProdutos++;

        System.out.println("\nProduto cadastrado com sucesso!");
        produto.exibirResumo(true); // Usa sobrecarga mostrando detalhes
    }

    private static void listarProdutos() {
        System.out.println("\n=== LISTA DE PRODUTOS ===");

        if (totalProdutos == 0) {
            System.out.println("Nenhum produto cadastrado ainda.");
            return;
        }

        for (int i = 0; i < totalProdutos; i++) {
            produtos[i].exibirResumo(); // Usa sobrecarga sem detalhes
        }

        System.out.println("\nTotal de produtos cadastrados: " + totalProdutos);
    }

    private static void buscarProdutoPorNome(Scanner scanner) {
        System.out.println("\n=== BUSCAR PRODUTO ===");
        System.out.print("Digite o nome do produto (ou parte dele): ");
        String termoBusca = scanner.nextLine();

        boolean encontrou = false;
        int contador = 0;

        for (int i = 0; i < totalProdutos; i++) {
            Produto p = produtos[i];

            // Usando método da classe Produto
            if (p.contemNome(termoBusca)) {
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

        while (indice < totalProdutos) {
            Produto p = produtos[indice];

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
        int estoqueTotal = 0;

        for (int i = 0; i < totalProdutos; i++) {
            Produto p = produtos[i];
            double preco = p.getPreco();

            somaPrecos += preco;
            estoqueTotal += p.getQuantidadeEstoque();

            if (preco > maiorPreco) {
                maiorPreco = preco;
            }

            if (preco < menorPreco) {
                menorPreco = preco;
            }

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
        System.out.println("Estoque total: " + estoqueTotal + " unidades");
        System.out.println("Fornecedores cadastrados: " + totalFornecedores);

        System.out.println("\n--- Analise do Catalogo ---");
        if (mediaPrecos < 100) {
            System.out.println("Catalogo com precos acessiveis");
        } else if (mediaPrecos >= 100 && mediaPrecos < 500) {
            System.out.println("Catalogo de preco medio");
        } else {
            System.out.println("Catalogo premium com produtos de alto valor");
        }

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

                break;
            }
        }

        if (!encontrou) {
            System.out.println("Produto com ID " + id + " nao foi encontrado.");
        }
    }

    private static void gerenciarEstoque(Scanner scanner) {
        System.out.println("\n=== GERENCIAR ESTOQUE ===");

        if (totalProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = buscarProdutoPorId(id);

        if (produto == null) {
            System.out.println("Produto nao encontrado!");
            return;
        }

        System.out.println("\nProduto selecionado:");
        produto.exibirResumo();

        System.out.println("\n1 - Adicionar estoque");
        System.out.println("2 - Remover estoque");
        System.out.print("Escolha uma opcao: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            produto.adicionarEstoque(quantidade); // Usa sobrecarga
        } else if (opcao == 2) {
            produto.removerEstoque(quantidade);
        } else {
            System.out.println("Opcao invalida!");
        }
    }

    private static void cadastrarFornecedor(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE FORNECEDOR ===");

        if (totalFornecedores >= fornecedores.length) {
            System.out.println("Limite de fornecedores atingido!");
            return;
        }

        System.out.print("Digite o nome do fornecedor: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CNPJ (14 digitos): ");
        String cnpj = scanner.nextLine();

        // Criando fornecedor com construtor de 3 parâmetros
        Fornecedor fornecedor = new Fornecedor(contadorIdFornecedor++, nome, cnpj);

        if (!fornecedor.validarCNPJ()) {
            System.out.println("CNPJ invalido! Cadastro cancelado.");
            return;
        }

        System.out.print("Digite o telefone (opcional): ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) {
            fornecedor.setTelefone(telefone);
        }

        System.out.print("Digite o email (opcional): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            fornecedor.setEmail(email);
            if (!fornecedor.validarEmail()) {
                System.out.println("Email invalido!");
            }
        }

        fornecedores[totalFornecedores] = fornecedor;
        totalFornecedores++;

        System.out.println("\nFornecedor cadastrado com sucesso!");
        fornecedor.exibirInformacoes();
    }

    private static void listarFornecedores() {
        System.out.println("\n=== LISTA DE FORNECEDORES ===");

        if (totalFornecedores == 0) {
            System.out.println("Nenhum fornecedor cadastrado ainda.");
            return;
        }

        for (int i = 0; i < totalFornecedores; i++) {
            fornecedores[i].exibirInformacoes();
        }

        System.out.println("\nTotal de fornecedores: " + totalFornecedores);
    }

    private static void associarFornecedor(Scanner scanner) {
        System.out.println("\n=== ASSOCIAR FORNECEDOR A PRODUTO ===");

        if (totalProdutos == 0 || totalFornecedores == 0) {
            System.out.println("E necessario ter produtos e fornecedores cadastrados!");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        int idProduto = scanner.nextInt();
        scanner.nextLine();

        Produto produto = buscarProdutoPorId(idProduto);

        if (produto == null) {
            System.out.println("Produto nao encontrado!");
            return;
        }

        listarFornecedores();
        System.out.print("\nDigite o ID do fornecedor: ");
        int idFornecedor = scanner.nextInt();
        scanner.nextLine();

        Fornecedor fornecedor = buscarFornecedorPorId(idFornecedor);

        if (fornecedor == null) {
            System.out.println("Fornecedor nao encontrado!");
            return;
        }

        produto.setFornecedor(fornecedor);
        fornecedor.adicionarProdutoFornecido();

        System.out.println("\nAssociacao realizada com sucesso!");
        System.out.println("\nProduto atualizado:");
        produto.exibirResumo(true);
    }

    private static void exibirProdutoDetalhado(Scanner scanner) {
        System.out.println("\n=== EXIBIR PRODUTO DETALHADO ===");

        if (totalProdutos == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = buscarProdutoPorId(id);

        if (produto == null) {
            System.out.println("Produto nao encontrado!");
            return;
        }

        // Exibe com detalhes (sobrecarga de método)
        produto.exibirResumo(true);

        // Exibe toString()
        System.out.println("\n--- toString() do produto ---");
        System.out.println(produto.toString());
    }

    // Métodos auxiliares
    private static Produto buscarProdutoPorId(int id) {
        for (int i = 0; i < totalProdutos; i++) {
            if (produtos[i].getId() == id) {
                return produtos[i];
            }
        }
        return null;
    }

    private static Fornecedor buscarFornecedorPorId(int id) {
        for (int i = 0; i < totalFornecedores; i++) {
            if (fornecedores[i].getId() == id) {
                return fornecedores[i];
            }
        }
        return null;
    }
}