package com.example.produto;

import com.example.produto.model.Produto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ProdutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdutoApplication.class, args);

        Scanner scanner = new Scanner(System.in);
        int contadorId = 1;

        System.out.println("=== Sistema de Cadastro de Produtos ===");

        while (true) {
            Produto produto = new Produto();

            System.out.print("\nDeseja cadastrar um novo produto? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("n")) {
                System.out.println("Encerrando o sistema...");
                break;
            }

            produto.setId(contadorId++); // ID automático

            System.out.print("Digite o nome do produto: ");
            produto.setNome(scanner.nextLine());

            System.out.print("Digite o preço do produto: ");
            produto.setPreco(scanner.nextDouble());

            System.out.print("O produto está disponível? (true/false): ");
            produto.setDisponivel(scanner.nextBoolean());
            scanner.nextLine(); // limpa o \n

            produto.exibirResumo();
        }

        scanner.close();
    }
}
