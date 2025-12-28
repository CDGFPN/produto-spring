package com.example.produto.service;

import com.example.produto.model.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Serviço responsável pela persistência de dados em arquivos
 * Demonstra manipulação de arquivos com tratamento de exceções
 */
public class PersistenciaService {

    // Constantes para formatação
    private static final String SEPARADOR = ";";

    /**
     * Salva lista de produtos em arquivo texto
     * Demonstra: FileWriter, BufferedWriter, try-with-resources, tratamento de IOException
     */
    public void salvarProdutos(ArrayList<Produto> produtos, String nomeArquivo) throws IOException {
        // Try-with-resources: fecha automaticamente os recursos
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {

            // Escreve cabeçalho
            writer.write("# Arquivo de Produtos - Sistema v4.0");
            writer.newLine();
            writer.write("# Formato: tipo;id;nome;preco;categoria;disponivel;estoque;idFornecedor;atributosEspecificos");
            writer.newLine();
            writer.newLine();

            // Escreve cada produto
            for (Produto p : produtos) {
                StringBuilder linha = new StringBuilder();

                // Campos comuns
                linha.append(p.getClass().getSimpleName()).append(SEPARADOR);
                linha.append(p.getId()).append(SEPARADOR);
                linha.append(p.getNome()).append(SEPARADOR);
                linha.append(p.getPreco()).append(SEPARADOR);
                linha.append(p.getCategoria() != null ? p.getCategoria().name() : "OUTROS").append(SEPARADOR);
                linha.append(p.isDisponivel()).append(SEPARADOR);
                linha.append(p.getQuantidadeEstoque()).append(SEPARADOR);
                linha.append(p.getFornecedor() != null ? p.getFornecedor().getId() : "0").append(SEPARADOR);

                // Atributos específicos por tipo
                if (p instanceof ProdutoDigital) {
                    ProdutoDigital pd = (ProdutoDigital) p;
                    linha.append(pd.getFormato()).append(SEPARADOR);
                    linha.append(pd.getTamanhoMB());
                } else if (p instanceof ProdutoFisico) {
                    ProdutoFisico pf = (ProdutoFisico) p;
                    linha.append(pf.getPeso()).append(SEPARADOR);
                    linha.append(pf.getComprimento()).append(SEPARADOR);
                    linha.append(pf.getLargura()).append(SEPARADOR);
                    linha.append(pf.getAltura());
                } else if (p instanceof ProdutoPerecivel) {
                    ProdutoPerecivel pp = (ProdutoPerecivel) p;
                    linha.append(pp.getDataValidade());
                }

                writer.write(linha.toString());
                writer.newLine();
            }

            writer.flush(); // Garante que tudo foi escrito

        } catch (IOException e) {
            // Propaga a exceção com mais contexto
            throw new IOException("Erro ao salvar produtos no arquivo '" + nomeArquivo + "': " + e.getMessage(), e);
        }
    }

    /**
     * Carrega produtos de arquivo texto
     * Demonstra: FileReader, BufferedReader, parsing de strings
     */
    public ArrayList<Produto> carregarProdutos(String nomeArquivo, ArrayList<Fornecedor> fornecedores)
            throws IOException {

        ArrayList<Produto> produtos = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        // Verifica se arquivo existe
        if (!arquivo.exists()) {
            System.out.println("Arquivo de produtos não encontrado: " + nomeArquivo);
            return produtos; // Retorna lista vazia
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {

            String linha;
            int numeroLinha = 0;

            while ((linha = reader.readLine()) != null) {
                numeroLinha++;

                // Ignora linhas de comentário e vazias
                if (linha.trim().isEmpty() || linha.startsWith("#")) {
                    continue;
                }

                try {
                    Produto produto = parsearLinhaProduto(linha, fornecedores);
                    if (produto != null) {
                        produtos.add(produto);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + numeroLinha + ": " + e.getMessage());
                    // Continua processando as outras linhas
                }
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        } catch (IOException e) {
            throw new IOException("Erro ao ler arquivo '" + nomeArquivo + "': " + e.getMessage(), e);
        }

        return produtos;
    }

    /**
     * Parseia uma linha de produto
     */
    private Produto parsearLinhaProduto(String linha, ArrayList<Fornecedor> fornecedores) {
        String[] partes = linha.split(SEPARADOR);

        if (partes.length < 8) {
            throw new IllegalArgumentException("Linha inválida: número insuficiente de campos");
        }

        try {
            String tipo = partes[0];
            int id = Integer.parseInt(partes[1]);
            String nome = partes[2];
            double preco = Double.parseDouble(partes[3]);
            Categoria categoria = Categoria.valueOf(partes[4]);
            boolean disponivel = Boolean.parseBoolean(partes[5]);
            int estoque = Integer.parseInt(partes[6]);
            int idFornecedor = Integer.parseInt(partes[7]);

            // Busca fornecedor
            Fornecedor fornecedor = null;
            if (idFornecedor > 0) {
                for (Fornecedor f : fornecedores) {
                    if (f.getId() == idFornecedor) {
                        fornecedor = f;
                        break;
                    }
                }
            }

            Produto produto = null;

            // Cria produto específico baseado no tipo
            switch (tipo) {
                case "ProdutoDigital":
                    if (partes.length >= 10) {
                        String formato = partes[8];
                        double tamanho = Double.parseDouble(partes[9]);
                        produto = new ProdutoDigital(id, nome, preco, categoria, fornecedor, formato, tamanho);
                    }
                    break;

                case "ProdutoFisico":
                    if (partes.length >= 12) {
                        double peso = Double.parseDouble(partes[8]);
                        double comp = Double.parseDouble(partes[9]);
                        double larg = Double.parseDouble(partes[10]);
                        double alt = Double.parseDouble(partes[11]);
                        produto = new ProdutoFisico(id, nome, preco, categoria, fornecedor, peso, comp, larg, alt);
                    }
                    break;

                case "ProdutoPerecivel":
                    if (partes.length >= 9) {
                        String validade = partes[8];
                        produto = new ProdutoPerecivel(id, nome, preco, categoria, fornecedor, validade);
                    }
                    break;

                default:
                    System.err.println("Tipo de produto desconhecido: " + tipo);
                    return null;
            }

            if (produto != null) {
                produto.setDisponivel(disponivel);
                produto.setQuantidadeEstoque(estoque);
            }

            return produto;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro ao converter números: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoria inválida: " + e.getMessage());
        }
    }

    /**
     * Salva fornecedores em arquivo
     */
    public void salvarFornecedores(ArrayList<Fornecedor> fornecedores, String nomeArquivo)
            throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {

            writer.write("# Arquivo de Fornecedores - Sistema v4.0");
            writer.newLine();
            writer.write("# Formato: tipo;id;nome;cnpj;telefone;email;ativo;produtosFornecidos;atributosEspecificos");
            writer.newLine();
            writer.newLine();

            for (Fornecedor f : fornecedores) {
                StringBuilder linha = new StringBuilder();

                linha.append(f.getClass().getSimpleName()).append(SEPARADOR);
                linha.append(f.getId()).append(SEPARADOR);
                linha.append(f.getNome()).append(SEPARADOR);
                linha.append(f.getCnpj()).append(SEPARADOR);
                linha.append(f.getTelefone() != null ? f.getTelefone() : "").append(SEPARADOR);
                linha.append(f.getEmail() != null ? f.getEmail() : "").append(SEPARADOR);
                linha.append(f.isAtivo()).append(SEPARADOR);
                linha.append(f.getQuantidadeProdutosFornecidos()).append(SEPARADOR);

                // Atributos específicos
                if (f instanceof FornecedorNacional) {
                    FornecedorNacional fn = (FornecedorNacional) f;
                    linha.append(fn.getEstado() != null ? fn.getEstado() : "").append(SEPARADOR);
                    linha.append(fn.obterAvaliacaoMedia()).append(SEPARADOR);
                    linha.append(fn.obterTotalAvaliacoes());
                } else if (f instanceof FornecedorInternacional) {
                    FornecedorInternacional fi = (FornecedorInternacional) f;
                    linha.append(fi.getPais() != null ? fi.getPais() : "").append(SEPARADOR);
                    linha.append(fi.getTaxaImportacao()).append(SEPARADOR);
                    linha.append(fi.obterAvaliacaoMedia()).append(SEPARADOR);
                    linha.append(fi.obterTotalAvaliacoes());
                }

                writer.write(linha.toString());
                writer.newLine();
            }

            writer.flush();

        } catch (IOException e) {
            throw new IOException("Erro ao salvar fornecedores: " + e.getMessage(), e);
        }
    }

    /**
     * Carrega fornecedores de arquivo
     */
    public ArrayList<Fornecedor> carregarFornecedores(String nomeArquivo) throws IOException {

        ArrayList<Fornecedor> fornecedores = new ArrayList<>();
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo de fornecedores não encontrado: " + nomeArquivo);
            return fornecedores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {

            String linha;
            int numeroLinha = 0;

            while ((linha = reader.readLine()) != null) {
                numeroLinha++;

                if (linha.trim().isEmpty() || linha.startsWith("#")) {
                    continue;
                }

                try {
                    Fornecedor fornecedor = parsearLinhaFornecedor(linha);
                    if (fornecedor != null) {
                        fornecedores.add(fornecedor);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha " + numeroLinha + ": " + e.getMessage());
                }
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        } catch (IOException e) {
            throw new IOException("Erro ao ler arquivo: " + e.getMessage(), e);
        }

        return fornecedores;
    }

    /**
     * Parseia linha de fornecedor
     */
    private Fornecedor parsearLinhaFornecedor(String linha) {
        String[] partes = linha.split(SEPARADOR);

        if (partes.length < 8) {
            throw new IllegalArgumentException("Linha inválida");
        }

        try {
            String tipo = partes[0];
            int id = Integer.parseInt(partes[1]);
            String nome = partes[2];
            String cnpj = partes[3];
            String telefone = partes[4].isEmpty() ? null : partes[4];
            String email = partes[5].isEmpty() ? null : partes[5];
            boolean ativo = Boolean.parseBoolean(partes[6]);
            int produtosFornecidos = Integer.parseInt(partes[7]);

            Fornecedor fornecedor = null;

            switch (tipo) {
                case "FornecedorNacional":
                    if (partes.length >= 11) {
                        String estado = partes[8].isEmpty() ? null : partes[8];
                        fornecedor = new FornecedorNacional(id, nome, cnpj, telefone, email, estado);

                        // Restaura avaliações
                        double somaAval = Double.parseDouble(partes[9]);
                        int totalAval = Integer.parseInt(partes[10]);
                        for (int i = 0; i < totalAval; i++) {
                            ((FornecedorNacional) fornecedor).avaliar(somaAval / totalAval);
                        }
                    }
                    break;

                case "FornecedorInternacional":
                    if (partes.length >= 12) {
                        String pais = partes[8].isEmpty() ? null : partes[8];
                        double taxa = Double.parseDouble(partes[9]);
                        fornecedor = new FornecedorInternacional(id, nome, cnpj, telefone, email, pais, taxa);

                        // Restaura avaliações
                        double somaAval = Double.parseDouble(partes[10]);
                        int totalAval = Integer.parseInt(partes[11]);
                        for (int i = 0; i < totalAval; i++) {
                            ((FornecedorInternacional) fornecedor).avaliar(somaAval / totalAval);
                        }
                    }
                    break;

                default:
                    System.err.println("Tipo de fornecedor desconhecido: " + tipo);
                    return null;
            }

            if (fornecedor != null) {
                fornecedor.setAtivo(ativo);
                fornecedor.setQuantidadeProdutosFornecidos(produtosFornecidos);
            }

            return fornecedor;

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao processar dados: " + e.getMessage());
        }
    }
}