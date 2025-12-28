package com.example.produto;

import com.example.produto.model.*;
import com.example.produto.exception.*;
import com.example.produto.service.PersistenciaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class ProdutoApplication {

    // Usando ArrayList ao invés de arrays fixos
    private static ArrayList<Produto> produtos = new ArrayList<>();
    private static ArrayList<Fornecedor> fornecedores = new ArrayList<>();
    private static int contadorIdProduto = 1;
    private static int contadorIdFornecedor = 1;

    // Serviço de persistência
    private static final PersistenciaService persistencia = new PersistenciaService();
    private static final String ARQUIVO_PRODUTOS = "produtos.txt";
    private static final String ARQUIVO_FORNECEDORES = "fornecedores.txt";

    public static void main(String[] args) {
        SpringApplication.run(ProdutoApplication.class, args);

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  Sistema de Cadastro de Produtos v4.0");
        System.out.println("  Com POO Avançada e Persistência");
        System.out.println("========================================");

        // Carregar dados salvos
        carregarDados();

        // Demonstração dos conceitos POO
        demonstrarPOOAvancada();

        // Loop principal com tratamento de exceções
        int opcao;
        do {
            try {
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
                    case 12:
                        salvarDados();
                        break;
                    case 13:
                        demonstrarPolimorfismo();
                        break;
                    case 0:
                        salvarDados();
                        System.out.println("\nEncerrando o sistema... Até logo!");
                        break;
                    default:
                        throw new OpcaoInvalidaException(opcao);
                }

            } catch (OpcaoInvalidaException e) {
                System.out.println("\n" + e.getMessage());
                opcao = -1; // Continua o loop
            } catch (Exception e) {
                System.out.println("\nErro inesperado: " + e.getMessage());
                scanner.nextLine(); // Limpa o buffer
                opcao = -1;
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
        System.out.println("4 - Filtrar produtos por faixa de preço");
        System.out.println("5 - Exibir relatório estatístico");
        System.out.println("6 - Atualizar disponibilidade");
        System.out.println("7 - Gerenciar estoque");
        System.out.println("11 - Exibir produto detalhado");
        System.out.println("\n=== FORNECEDORES ===");
        System.out.println("8 - Cadastrar fornecedor");
        System.out.println("9 - Listar fornecedores");
        System.out.println("10 - Associar fornecedor a produto");
        System.out.println("\n=== SISTEMA ===");
        System.out.println("12 - Salvar dados");
        System.out.println("13 - Demonstrar Polimorfismo");
        System.out.println("\n0 - Sair");
        System.out.println("====================================");
        System.out.print("Escolha uma opção: ");
    }

    // Demonstração de POO Avançada
    private static void demonstrarPOOAvancada() {
        System.out.println("\n=== DEMONSTRAÇÃO POO AVANÇADA ===");

        try {
            // Herança e Polimorfismo
            System.out.println("\n--- 1. HERANÇA E POLIMORFISMO ---");

            // Criando produtos especializados
            ProdutoDigital digital = new ProdutoDigital(9991, "E-book Java", 29.90,
                    Categoria.LIVROS, null, "PDF", 15.5);

            ProdutoFisico fisico = new ProdutoFisico(9992, "Notebook Dell", 3500.00,
                    Categoria.ELETRONICOS, null, 2.5, 35, 25, 5);

            ProdutoPerecivel perecivel = new ProdutoPerecivel(9993, "Leite Integral", 4.50,
                    Categoria.ALIMENTOS, null, "2025-12-31");

            digital.exibirResumo();
            fisico.exibirResumo();
            perecivel.exibirResumo();

            // Demonstrando polimorfismo
            System.out.println("\n--- 2. POLIMORFISMO POR SUBTIPAGEM ---");
            ArrayList<Produto> listaMista = new ArrayList<>();
            listaMista.add(digital);
            listaMista.add(fisico);
            listaMista.add(perecivel);

            System.out.println("Iterando por produtos polimórficos:");
            for (Produto p : listaMista) {
                System.out.println("\nTipo: " + p.getClass().getSimpleName());
                System.out.println("Nome: " + p.getNome());
                System.out.println("Preço: R$ " + String.format("%.2f", p.getPreco()));

                // Polimorfismo - cada classe tem sua implementação
                if (p instanceof Descritivel) {
                    System.out.println("Descrição: " + ((Descritivel) p).obterDescricaoCompleta());
                }
            }

            // Interfaces
            System.out.println("\n--- 3. INTERFACES ---");
            demonstrarInterfaces(digital, fisico);

            // Classes abstratas
            System.out.println("\n--- 4. ABSTRAÇÃO ---");
            System.out.println("ProdutoDigital é subclasse de Produto (abstrata): " +
                    (digital instanceof Produto));

        } catch (Exception e) {
            System.out.println("Erro na demonstração: " + e.getMessage());
        }

        System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===\n");
    }

    private static void demonstrarInterfaces(Produto p1, Produto p2) {
        System.out.println("Demonstrando interface Descritivel:");

        if (p1 instanceof Descritivel) {
            Descritivel desc1 = (Descritivel) p1;
            System.out.println("\nProduto 1:");
            System.out.println("Descrição curta: " + desc1.obterDescricaoCurta());
            System.out.println("Descrição completa: " + desc1.obterDescricaoCompleta());
        }

        if (p2 instanceof Descritivel) {
            Descritivel desc2 = (Descritivel) p2;
            System.out.println("\nProduto 2:");
            System.out.println("Descrição curta: " + desc2.obterDescricaoCurta());
            System.out.println("Descrição completa: " + desc2.obterDescricaoCompleta());
        }
    }

    private static void demonstrarPolimorfismo() {
        System.out.println("\n=== DEMONSTRAÇÃO DE POLIMORFISMO ===");

        try {
            // Criando diferentes fornecedores
            FornecedorNacional nacional = new FornecedorNacional(
                    9991, "Tech Brasil LTDA", "12345678901234",
                    "(11) 9999-8888", "contato@techbr.com", "SP");

            FornecedorInternacional internacional = new FornecedorInternacional(
                    9992, "Global Tech Inc", "98765432109876",
                    "+1 555-1234", "info@globaltech.com", "USA", 5.5);

            // Polimorfismo com lista de fornecedores
            ArrayList<Fornecedor> fornecedoresMistos = new ArrayList<>();
            fornecedoresMistos.add(nacional);
            fornecedoresMistos.add(internacional);

            System.out.println("\nIterando por fornecedores polimórficos:");
            for (Fornecedor f : fornecedoresMistos) {
                System.out.println("\n" + "=".repeat(40));
                f.exibirInformacoes(); // Método sobrescrito

                // Comportamento específico
                if (f instanceof Avaliavel) {
                    Avaliavel av = (Avaliavel) f;
                    av.avaliar(4.5);
                    System.out.println("Avaliação: " + av.obterAvaliacaoMedia());
                }
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===");
    }

    private static void cadastrarProduto(Scanner scanner) {
        System.out.println("\n=== CADASTRO DE PRODUTO ===");

        try {
            System.out.print("Digite o nome do produto: ");
            String nome = scanner.nextLine();

            if (nome == null || nome.trim().isEmpty()) {
                throw new DadosInvalidosException("Nome não pode ser vazio!");
            }

            System.out.print("Digite o preço do produto: R$ ");
            double preco = scanner.nextDouble();
            scanner.nextLine();

            if (preco <= 0) {
                throw new PrecoInvalidoException(preco);
            }

            // Escolher tipo de produto
            System.out.println("\nTipo de produto:");
            System.out.println("1 - Produto Digital");
            System.out.println("2 - Produto Físico");
            System.out.println("3 - Produto Perecível");
            System.out.print("Escolha: ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            Produto produto = null;

            // Categoria
            System.out.println("\nCategorias disponíveis:");
            for (Categoria cat : Categoria.values()) {
                System.out.println((cat.ordinal() + 1) + " - " + cat.getDescricao());
            }
            System.out.print("Escolha a categoria (1-5): ");
            int categoriaOpcao = scanner.nextInt();
            scanner.nextLine();
            Categoria categoria = Categoria.porOpcao(categoriaOpcao);

            // Criar produto específico
            switch (tipo) {
                case 1:
                    System.out.print("Formato do arquivo (PDF, EPUB, MP3, etc): ");
                    String formato = scanner.nextLine();
                    System.out.print("Tamanho do arquivo (MB): ");
                    double tamanho = scanner.nextDouble();
                    scanner.nextLine();

                    produto = new ProdutoDigital(contadorIdProduto++, nome, preco,
                            categoria, null, formato, tamanho);
                    break;

                case 2:
                    System.out.print("Peso (kg): ");
                    double peso = scanner.nextDouble();
                    System.out.print("Comprimento (cm): ");
                    double comp = scanner.nextDouble();
                    System.out.print("Largura (cm): ");
                    double larg = scanner.nextDouble();
                    System.out.print("Altura (cm): ");
                    double alt = scanner.nextDouble();
                    scanner.nextLine();

                    produto = new ProdutoFisico(contadorIdProduto++, nome, preco,
                            categoria, null, peso, comp, larg, alt);
                    break;

                case 3:
                    System.out.print("Data de validade (YYYY-MM-DD): ");
                    String validade = scanner.nextLine();

                    produto = new ProdutoPerecivel(contadorIdProduto++, nome, preco,
                            categoria, null, validade);
                    break;

                default:
                    throw new OpcaoInvalidaException(tipo);
            }

            System.out.print("Quantidade inicial em estoque: ");
            int estoque = scanner.nextInt();
            scanner.nextLine();
            produto.setQuantidadeEstoque(estoque);

            System.out.print("O produto está disponível? (true/false): ");
            produto.setDisponivel(scanner.nextBoolean());
            scanner.nextLine();

            // Associar fornecedor
            if (!fornecedores.isEmpty()) {
                System.out.print("\nDeseja associar um fornecedor? (s/n): ");
                String resp = scanner.nextLine();
                if (resp.equalsIgnoreCase("s")) {
                    listarFornecedores();
                    System.out.print("Digite o ID do fornecedor: ");
                    int idFornecedor = scanner.nextInt();
                    scanner.nextLine();

                    Fornecedor fornecedor = buscarFornecedorPorId(idFornecedor);
                    if (fornecedor == null) {
                        throw new FornecedorNaoEncontradoException(idFornecedor);
                    }

                    produto.setFornecedor(fornecedor);
                    fornecedor.adicionarProdutoFornecido();
                }
            }

            produtos.add(produto);
            System.out.println("\nProduto cadastrado com sucesso!");
            produto.exibirResumo(true);

        } catch (PrecoInvalidoException | DadosInvalidosException |
                 OpcaoInvalidaException | FornecedorNaoEncontradoException e) {
            System.out.println("\nErro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nErro inesperado: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void listarProdutos() {
        System.out.println("\n=== LISTA DE PRODUTOS ===");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado ainda.");
            return;
        }

        for (Produto p : produtos) {
            p.exibirResumo();
        }

        System.out.println("\nTotal de produtos cadastrados: " + produtos.size());
    }

    private static void buscarProdutoPorNome(Scanner scanner) {
        System.out.println("\n=== BUSCAR PRODUTO ===");
        System.out.print("Digite o nome do produto (ou parte dele): ");
        String termoBusca = scanner.nextLine();

        ArrayList<Produto> encontrados = new ArrayList<>();

        for (Produto p : produtos) {
            if (p.contemNome(termoBusca)) {
                encontrados.add(p);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum produto encontrado com esse nome.");
        } else {
            System.out.println("\nProdutos encontrados:");
            for (Produto p : encontrados) {
                p.exibirResumo();
            }
            System.out.println("\nTotal: " + encontrados.size());
        }
    }

    private static void filtrarPorPreco(Scanner scanner) {
        try {
            System.out.println("\n=== FILTRAR POR PREÇO ===");
            System.out.print("Digite o preço mínimo: R$ ");
            double precoMin = scanner.nextDouble();
            System.out.print("Digite o preço máximo: R$ ");
            double precoMax = scanner.nextDouble();
            scanner.nextLine();

            if (precoMin < 0 || precoMax < 0 || precoMin > precoMax) {
                throw new DadosInvalidosException("Faixa de preço inválida!");
            }

            ArrayList<Produto> filtrados = new ArrayList<>();

            for (Produto p : produtos) {
                if (p.getPreco() >= precoMin && p.getPreco() <= precoMax) {
                    filtrados.add(p);
                }
            }

            if (filtrados.isEmpty()) {
                System.out.println("Nenhum produto encontrado nessa faixa de preço.");
            } else {
                System.out.println("\nProdutos na faixa de R$ " + String.format("%.2f", precoMin) +
                        " a R$ " + String.format("%.2f", precoMax) + ":");
                for (Produto p : filtrados) {
                    p.exibirResumo();
                }
                System.out.println("\nTotal: " + filtrados.size());
            }

        } catch (DadosInvalidosException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void exibirRelatorio() {
        System.out.println("\n=== RELATÓRIO ESTATÍSTICO ===");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        double somaPrecos = 0;
        double maiorPreco = produtos.get(0).getPreco();
        double menorPreco = produtos.get(0).getPreco();
        int disponiveis = 0;
        int indisponiveis = 0;
        int estoqueTotal = 0;

        for (Produto p : produtos) {
            double preco = p.getPreco();

            somaPrecos += preco;
            estoqueTotal += p.getQuantidadeEstoque();

            if (preco > maiorPreco) maiorPreco = preco;
            if (preco < menorPreco) menorPreco = preco;

            if (p.isDisponivel()) {
                disponiveis++;
            } else {
                indisponiveis++;
            }
        }

        double mediaPrecos = somaPrecos / produtos.size();

        System.out.println("Total de produtos: " + produtos.size());
        System.out.println("Preço médio: R$ " + String.format("%.2f", mediaPrecos));
        System.out.println("Maior preço: R$ " + String.format("%.2f", maiorPreco));
        System.out.println("Menor preço: R$ " + String.format("%.2f", menorPreco));
        System.out.println("Produtos disponíveis: " + disponiveis);
        System.out.println("Produtos indisponíveis: " + indisponiveis);
        System.out.println("Estoque total: " + estoqueTotal + " unidades");
        System.out.println("Fornecedores cadastrados: " + fornecedores.size());
    }

    private static void atualizarDisponibilidade(Scanner scanner) {
        try {
            System.out.println("\n=== ATUALIZAR DISPONIBILIDADE ===");

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                return;
            }

            System.out.print("Digite o ID do produto: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Produto p = buscarProdutoPorId(id);
            if (p == null) {
                throw new ProdutoNaoEncontradoException(id);
            }

            System.out.println("\nProduto encontrado:");
            p.exibirResumo();

            System.out.print("\nNovo status de disponibilidade (true/false): ");
            boolean novoStatus = scanner.nextBoolean();
            scanner.nextLine();

            p.setDisponivel(novoStatus);
            System.out.println("\nDisponibilidade atualizada com sucesso!");
            p.exibirResumo();

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void gerenciarEstoque(Scanner scanner) {
        try {
            System.out.println("\n=== GERENCIAR ESTOQUE ===");

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                return;
            }

            System.out.print("Digite o ID do produto: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Produto produto = buscarProdutoPorId(id);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(id);
            }

            System.out.println("\nProduto selecionado:");
            produto.exibirResumo();

            System.out.println("\n1 - Adicionar estoque");
            System.out.println("2 - Remover estoque");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Digite a quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            if (quantidade <= 0) {
                throw new DadosInvalidosException("Quantidade deve ser positiva!");
            }

            if (opcao == 1) {
                produto.adicionarEstoque(quantidade);
            } else if (opcao == 2) {
                if (!produto.removerEstoque(quantidade)) {
                    throw new EstoqueInsuficienteException(produto.getNome(),
                            quantidade, produto.getQuantidadeEstoque());
                }
            } else {
                throw new OpcaoInvalidaException(opcao);
            }

        } catch (ProdutoNaoEncontradoException | DadosInvalidosException |
                 EstoqueInsuficienteException | OpcaoInvalidaException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void cadastrarFornecedor(Scanner scanner) {
        try {
            System.out.println("\n=== CADASTRO DE FORNECEDOR ===");

            System.out.print("Digite o nome do fornecedor: ");
            String nome = scanner.nextLine();

            if (nome == null || nome.trim().isEmpty()) {
                throw new DadosInvalidosException("Nome não pode ser vazio!");
            }

            System.out.print("Digite o CNPJ (14 dígitos): ");
            String cnpj = scanner.nextLine();

            // Escolher tipo de fornecedor
            System.out.println("\nTipo de fornecedor:");
            System.out.println("1 - Nacional");
            System.out.println("2 - Internacional");
            System.out.print("Escolha: ");
            int tipo = scanner.nextInt();
            scanner.nextLine();

            Fornecedor fornecedor = null;

            switch (tipo) {
                case 1:
                    System.out.print("Estado (UF): ");
                    String estado = scanner.nextLine();
                    fornecedor = new FornecedorNacional(contadorIdFornecedor++, nome, cnpj,
                            "", "", estado);
                    break;

                case 2:
                    System.out.print("País: ");
                    String pais = scanner.nextLine();
                    System.out.print("Taxa de importação (%): ");
                    double taxa = scanner.nextDouble();
                    scanner.nextLine();
                    fornecedor = new FornecedorInternacional(contadorIdFornecedor++, nome, cnpj,
                            "", "", pais, taxa);
                    break;

                default:
                    throw new OpcaoInvalidaException(tipo);
            }

            if (!fornecedor.validarCNPJ()) {
                throw new DadosInvalidosException("CNPJ inválido!");
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
                    System.out.println("Aviso: Email inválido!");
                }
            }

            fornecedores.add(fornecedor);
            System.out.println("\nFornecedor cadastrado com sucesso!");
            fornecedor.exibirInformacoes();

        } catch (DadosInvalidosException | OpcaoInvalidaException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void listarFornecedores() {
        System.out.println("\n=== LISTA DE FORNECEDORES ===");

        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado ainda.");
            return;
        }

        for (Fornecedor f : fornecedores) {
            f.exibirInformacoes();
        }

        System.out.println("\nTotal de fornecedores: " + fornecedores.size());
    }

    private static void associarFornecedor(Scanner scanner) {
        try {
            System.out.println("\n=== ASSOCIAR FORNECEDOR A PRODUTO ===");

            if (produtos.isEmpty() || fornecedores.isEmpty()) {
                throw new DadosInvalidosException(
                        "É necessário ter produtos e fornecedores cadastrados!");
            }

            System.out.print("Digite o ID do produto: ");
            int idProduto = scanner.nextInt();
            scanner.nextLine();

            Produto produto = buscarProdutoPorId(idProduto);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(idProduto);
            }

            listarFornecedores();
            System.out.print("\nDigite o ID do fornecedor: ");
            int idFornecedor = scanner.nextInt();
            scanner.nextLine();

            Fornecedor fornecedor = buscarFornecedorPorId(idFornecedor);
            if (fornecedor == null) {
                throw new FornecedorNaoEncontradoException(idFornecedor);
            }

            produto.setFornecedor(fornecedor);
            fornecedor.adicionarProdutoFornecido();

            System.out.println("\nAssociação realizada com sucesso!");
            produto.exibirResumo(true);

        } catch (DadosInvalidosException | ProdutoNaoEncontradoException |
                 FornecedorNaoEncontradoException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void exibirProdutoDetalhado(Scanner scanner) {
        try {
            System.out.println("\n=== EXIBIR PRODUTO DETALHADO ===");

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                return;
            }

            System.out.print("Digite o ID do produto: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Produto produto = buscarProdutoPorId(id);
            if (produto == null) {
                throw new ProdutoNaoEncontradoException(id);
            }

            produto.exibirResumo(true);
            System.out.println("\n--- toString() do produto ---");
            System.out.println(produto.toString());

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // Métodos de persistência
    private static void salvarDados() {
        try {
            System.out.println("\n=== SALVANDO DADOS ===");

            persistencia.salvarProdutos(produtos, ARQUIVO_PRODUTOS);
            persistencia.salvarFornecedores(fornecedores, ARQUIVO_FORNECEDORES);

            System.out.println("Dados salvos com sucesso!");
            System.out.println("- " + produtos.size() + " produtos salvos");
            System.out.println("- " + fornecedores.size() + " fornecedores salvos");

        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void carregarDados() {
        try {
            System.out.println("\n=== CARREGANDO DADOS ===");

            // Carregar fornecedores primeiro
            ArrayList<Fornecedor> fornecedoresCarregados =
                    persistencia.carregarFornecedores(ARQUIVO_FORNECEDORES);

            if (!fornecedoresCarregados.isEmpty()) {
                fornecedores = fornecedoresCarregados;
                // Atualizar contador
                int maxId = 0;
                for (Fornecedor f : fornecedores) {
                    if (f.getId() > maxId) maxId = f.getId();
                }
                contadorIdFornecedor = maxId + 1;
                System.out.println("- " + fornecedores.size() + " fornecedores carregados");
            }

            // Carregar produtos
            ArrayList<Produto> produtosCarregados =
                    persistencia.carregarProdutos(ARQUIVO_PRODUTOS, fornecedores);

            if (!produtosCarregados.isEmpty()) {
                produtos = produtosCarregados;
                // Atualizar contador
                int maxId = 0;
                for (Produto p : produtos) {
                    if (p.getId() > maxId) maxId = p.getId();
                }
                contadorIdProduto = maxId + 1;
                System.out.println("- " + produtos.size() + " produtos carregados");
            }

            if (fornecedores.isEmpty() && produtos.isEmpty()) {
                System.out.println("Nenhum dado anterior encontrado. Iniciando sistema vazio.");
            }

        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível carregar dados anteriores.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    // Métodos auxiliares
    private static Produto buscarProdutoPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private static Fornecedor buscarFornecedorPorId(int id) {
        for (Fornecedor f : fornecedores) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }
}