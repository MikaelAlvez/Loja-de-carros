package LojaDeCarros;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import LojaDeCarros.Carro.CategoriaCarro;

public class Menu {
    private ServidorAutenticacao servidorAutenticacao;
    private Servidor servidor;
    private Scanner scanner;

    public Menu() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4096);
            servidorAutenticacao = (ServidorAutenticacao) registry.lookup("ServidorAutenticacao");
            servidor = (Servidor) registry.lookup("Servidor");
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void exibirMenu() {
        if (realizarAutenticacao()) {
	        int opcao;
	        do {
	            System.out.println("===== Menu Principal =====");
	            System.out.println("1. Adicionar carro");
	            System.out.println("2. Apagar carro");
	            System.out.println("3. Listar carros");
	            System.out.println("4. Pesquisar carro");
	            System.out.println("5. Alterar atributos de carro");
	            System.out.println("6. Atualizar listagem de carros");
	            System.out.println("7. Exibir quantidade de carros");
	            System.out.println("8. Comprar carro");
	            System.out.println("9. Sair");
	            System.out.print("Escolha uma opção: ");
	            opcao = scanner.nextInt();
	            scanner.nextLine();
	
	            switch (opcao) {
	                case 1:
	                    adicionarCarro();
	                    break;
	                case 2:
	                	
	                    break;
	                case 3:
	
	                	break;
	                case 4:
	
	                	break;
	                case 5:
	
	                	break;
	                case 6:
	
	                	break;
	                case 7:
	
	                	break;
	                case 8:
	
	                	break;
	                case 9:
	                    System.out.println("Saindo...");
	                    break;
	                default:
	                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
	            }
	        } while (opcao != 9);
        }
    }
    
    private boolean realizarAutenticacao() {
        try {
            System.out.print("Usuário: ");
            String nomeUsuario = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            return servidorAutenticacao.autenticar(nomeUsuario, senha);
        } catch (Exception e) {
            System.out.println("\nErro ao realizar autenticação: " + e.getMessage());
            return false;
        }
    }
    
    private void adicionarCarro() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nRenavan: ");
            String renavan = scanner.nextLine();
            System.out.print("Nome do carro: ");
            String nome = scanner.nextLine();
            System.out.print("Categoria (ECONOMICO, INTERMEDIARIO, EXECUTIVO): ");
            String categoriaStr = scanner.nextLine();
            CategoriaCarro categoria = CategoriaCarro.valueOf(categoriaStr.toUpperCase());
            System.out.print("Ano de fabricação: ");
            int anoFabricacao = scanner.nextInt();
            System.out.print("Quantidade disponível: ");
            int quantidadeDisponivel = scanner.nextInt();
            System.out.print("Preço: ");
            double preco = scanner.nextDouble();

            Carro novoCarro = new Carro(renavan, nome, categoria, anoFabricacao, quantidadeDisponivel, preco);
            servidor.adicionarCarro(novoCarro);
            System.out.println("\nCarro adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao adicionar carro: " + e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}

