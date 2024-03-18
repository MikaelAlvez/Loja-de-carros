package LojaDeCarros;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ServidorLojadeCarros {
    private Map<String, Carro> carros;
    private List<Socket> clientesConectados;
    private static final int PORTA_SERVIDOR_GATEWAY = 4096;
    
    public ServidorLojadeCarros() {
        carros = new HashMap<>();
        clientesConectados = new ArrayList<>();
    }
    
    public void iniciar() throws IOException {     
    	try (ServerSocket servidorSocket = new ServerSocket(4097)) {
            System.out.println("Servidor de Loja de Carros iniciado na porta 4097.");
            
            Socket gatewaySocket = new Socket("localhost", PORTA_SERVIDOR_GATEWAY);
            System.out.println("Conectado ao ServidorGateway na porta " + PORTA_SERVIDOR_GATEWAY);

            clientesConectados.add(gatewaySocket);

        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            exibirMenu();
            System.out.print("Digite a opção desejada: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                	adicionarCarro();
                	break;
                case 2:
                	apagarCarro();
                	break;
                case 3:
                	listarTodosCarros();
                	break;
                case 4:
                	pesquisarCarrosPorNome();
                	break;
                case 5:
                	alterarAtributosCarro();
                	break;
                case 6:
                	quantidadeDeCarros();
                	break;
                case 7:
                	comprarCarro();
                	break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, digite novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    	}
    }

    private static void exibirMenu() {
        System.out.println("\n1. Adicionar carro");
        System.out.println("2. Apagar carro");
        System.out.println("3. Listar carros");
        System.out.println("4. Pesquisar (consultar) carro");
        System.out.println("5. Alterar atributos de carros");
        System.out.println("6. Exibir quantidade de carros");
        System.out.println("7. Comprar carro");
        System.out.println("0. Sair");
    }

    public void adicionarCarro() {
        try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Digite o renavan do carro:");
			String renavan = scanner.nextLine();
			System.out.println("Digite o nome do carro:");
			String nome = scanner.nextLine();
			System.out.println("Digite a categoria do carro:");
			String categoria = scanner.nextLine();
			System.out.println("Digite o ano de fabricação do carro:");
			int anoFabricacao = scanner.nextInt();
			System.out.println("Digite o preço do carro:");
			double preco = scanner.nextDouble();
			
			Carro novoCarro = new Carro(renavan, nome, categoria, anoFabricacao, preco);
			System.out.println("\nCarro adicionado com sucesso!");
		}
    }


    public void apagarCarro() {
    	Scanner scanner = new Scanner(System.in);
    	System.out.print("Digite o número do Renavan do carro que deseja apagar: ");
    	String renavan = scanner.nextLine();
    	
    	carros.remove(renavan);
    }

    public List<Carro> listarCarrosPorCategoria(String categoria) {
        List<Carro> carrosPorCategoria = new ArrayList<>();
        for (Carro carro : carros.values()) {
            if (carro.getCategoria().equalsIgnoreCase(categoria)) {
                carrosPorCategoria.add(carro);
            }
        }
        Collections.sort(carrosPorCategoria, Comparator.comparing(Carro::getNome));
        return carrosPorCategoria;
    }

    public List<Carro> listarTodosCarros() {
        List<Carro> todosCarros = new ArrayList<>(carros.values());
        Collections.sort(todosCarros, Comparator.comparing(Carro::getNome));
        return todosCarros;
    }

    public List<Carro> pesquisarCarrosPorNome() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do carro que deseja pesquisar: ");
        String nome = scanner.nextLine();
        scanner.close();

        List<Carro> carrosEncontrados = new ArrayList<>();

        for (Carro carro : carros.values()) {
            if (carro.getNome().equalsIgnoreCase(nome)) {
                carrosEncontrados.add(carro);
            }
        }

        return carrosEncontrados;
    }

    public Carro pesquisarCarroPorRenavan(String renavan) {
        return carros.get(renavan);
    }

    public void alterarAtributosCarro() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número do Renavam do carro que deseja alterar: ");
        String renavam = scanner.nextLine();

        Carro carro = carros.get(renavam);
        if (carro != null) {
            System.out.println("Carro encontrado. Selecione os campos que deseja editar:");
            System.out.println("1. Nome");
            System.out.println("2. Categoria");
            System.out.println("3. Ano de Fabricação");
            System.out.println("4. Preço");
            System.out.print("Digite o número do campo que deseja editar: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o novo nome do carro: ");
                    String novoNome = scanner.nextLine();
                    carro.setNome(novoNome);
                    break;
                case 2:
                    System.out.print("Digite a nova categoria do carro: ");
                    String novaCategoria = scanner.nextLine();
                    carro.setCategoria(novaCategoria);
                    break;
                case 3:
                    System.out.print("Digite o novo ano de fabricação do carro: ");
                    int novoAnoFabricacao = scanner.nextInt();
                    carro.setAnoFabricacao(novoAnoFabricacao);
                    break;
                case 4:
                    System.out.print("Digite o novo preço do carro: ");
                    double novoPreco = scanner.nextDouble();
                    carro.setPreco(novoPreco);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            System.out.println("Atributos do carro atualizados com sucesso.");
        } else {
            System.out.println("Carro não encontrado.");
        }
    }


    public int quantidadeDeCarros() {
        return carros.size();
    }

    public void atualizarClientes(List<Carro> listaCarros) {
        for (Socket cliente : clientesConectados) {
            try {
                ObjectOutputStream outToClient = new ObjectOutputStream(cliente.getOutputStream());
                outToClient.writeObject(listaCarros);
                outToClient.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean comprarCarro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número do Renavan do carro que deseja comprar: ");
        String renavan = scanner.nextLine();
        
        Carro carro = carros.get(renavan);
        if (carro != null && carro.getQuantidadeDisponivel() > 0) {
            carro.setQuantidadeDisponivel(carro.getQuantidadeDisponivel() - 1);
            System.out.println("Carro comprado com sucesso!");
            return true;
        } else {
            System.out.println("O carro não está disponível para compra ou não existe.");
            return false;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServidorLojadeCarros{");
        builder.append("Quantidade de carros: ").append(carros.size()).append("\n");
        builder.append("Carros:\n");
        for (Carro carro : carros.values()) {
            builder.append(carro).append("\n");
        }
        builder.append("}");
        return builder.toString();
    }
    public class Main {
        public static void main(String[] args) throws IOException {
            ServidorLojadeCarros servidor = new ServidorLojadeCarros();
            servidor.iniciar();

            ServidorGateway servidorGateway = new ServidorGateway();
        }
    }
}
