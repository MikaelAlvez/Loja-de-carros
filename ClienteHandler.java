package LojaDeCarros;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteHandler {
	
	private static GatewayRemoto gatewayRemoto;
	private static boolean conexao = false;
	
	private static Usuarios usuario;
	private static List<TiposCarros> Carro;
	
	private static Scanner scan;
	
	public static void main(String[] args) {
	    scan = new Scanner(System.in);
		Carro = new ArrayList<TiposCarros>();
		
		try {
			Registry authRegister = LocateRegistry.getRegistry(4098);
			gatewayRemoto = (GatewayRemoto) authRegister.lookup("Gateway");
			
			System.out.println("---LOJA DE CARROS---\n");
			usuario = login();
					if(usuario != null) {
						conexao = true;
					}
				
			while(conexao) {
				if(usuario.funcionario()) {
				System.out.println("Bem-vindo ao sistema de loja de carro\n" +
						"\n1. Cadastrar Carro\n" +
						"2. Remover carro\n" +
						"3. Listar carros\n" +
						"4. Consultar carros\n" +
						"5. Editar carro\n" +
						"6. Carros Vendidos" +
						"7. Quantidade de carros disponíveis\n" +
						"8. Realizar compra\n");
				}else {
					System.out.println("\n3. Listar carros\n" +
							"4. Consultar carros\n" +
							"7. Quantidade de carros disponíveis\n" +
							"8. Realizar compra\n");
					}
				
				scan = new Scanner(System.in);
				System.out.print("Opção: ");
				int opc = scan.nextInt();
				scan.nextLine();
				
				switch(opc) {
				case 1:
					if(usuario.funcionario()) {
						AcidionarCarro();
					}
					break;
				case 2:
					if(usuario.funcionario()) {
						RemoverCarro();
					}
					break;
				case 3:
					List<TiposCarros> carrosRegistrados = new ArrayList<TiposCarros>();
					
					carrosRegistrados = gatewayRemoto.ListaDeCarros();
					System.out.println("\nListar por Categoria:\n"+
						"1. Categoria Economica\n" + 
						"2. Categoria Intermediária\n" +
						"3. Categoria Executiva");
					System.out.print("Opção: ");
					int categoria = scan.nextInt();
					scan.nextLine();
					
					if(categoria >=1 && categoria<=3) {
						carrosRegistrados = gatewayRemoto.ListaPorCategoria(categoria);
						for(TiposCarros carro : carrosRegistrados) {
							System.out.println("\nNome: " + carro.getNome() +
									"\nCategoria: " + carro.getTipoCategoria() +
									"\nAno de fabricação: " + carro.getAno() +
									"\nRenavam: " + carro.getRenavam() +
									"\nPreço: R$" + carro.getPreco());
						}
					}else {
						System.out.println("Opção inválida!");
					}
					break;
				case 4:
					System.out.println("Informe o número do Renavam");
					String renavam = scan.nextLine();
		
						TiposCarros consultarCarro = gatewayRemoto.ConsultarCarro(renavam);
						if(consultarCarro != null) {
							System.out.println("\nNome: " + consultarCarro.getNome() +
								"\nCategoria: " + consultarCarro.getTipoCategoria() +
								"\nAno de fabricação: " + consultarCarro.getAno() +
								"\nRenavam: " + consultarCarro.getRenavam() +
								"\nPreço: R$" + consultarCarro.getPreco());
						} else {
							System.out.println("Carro não encontrado.");
						}
					break;
				case 5:
					if(usuario.funcionario()) {
						EditarCarro();
					}
					break;
				case 6:
					for(TiposCarros vendidos : Carro) {
						System.out.println("Nome: " + vendidos.getNome() +
							"\nCategoria: " + vendidos.getTipoCategoria() +
							"\nAno de fabricação: " + vendidos.getAno() +
							"\nRenavam: " + vendidos.getRenavam() +
							"\nPreço: R$" + vendidos.getPreco());
					}
					break;
				case 7:
					int total = gatewayRemoto.quantidadeCarros(1) + gatewayRemoto.quantidadeCarros(2) + gatewayRemoto.quantidadeCarros(3);
					System.out.println("Quantidade total de carros disponíveis: " + total);
					break;
				case 8:
					ComprarCarro();
					break;
				default:
					System.out.println("Opção inválida!");
				}
			}
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private static Usuarios login() throws RemoteException {
		System.out.print("Usuario: ");
		String usuario = scan.nextLine();
		System.out.print("Senha: ");
		String senha = scan.nextLine();
		
		Usuarios conectar = gatewayRemoto.login(usuario, senha);
		
		return conectar;
		
	}
	
	private static void AcidionarCarro() throws RemoteException {
		System.out.print("Nome: ");
		String nomeCarro = scan.nextLine();
		
		System.out.println("\n1. Categoria Economica");
		System.out.println("2. Categoria Intermediária");
		System.out.println("3. Categoria Executiva");
		System.out.print("Categoria: ");
		int categoria = scan.nextInt();
		scan.nextLine();
		
		if(categoria>=1 && categoria>=3) {
			System.out.print("\nAno de fabricação: ");
			String ano = scan.nextLine();
			System.out.print("Renavam: ");
			String renavam = scan.nextLine();
			System.out.print("Preço: R$");
			double preco = scan.nextDouble();
			scan.nextLine();
			
			if(categoria==1) {
				gatewayRemoto.AdicionarCarro(new CategoriaEconomica(nomeCarro, categoria, ano, renavam, preco));
			}
			if(categoria==2) {
				gatewayRemoto.AdicionarCarro(new CategoriaIntermediaria(nomeCarro, categoria, ano, renavam, preco));
			}
			if(categoria==3) {
				gatewayRemoto.AdicionarCarro(new CategoriaExecutiva(nomeCarro, categoria, ano, renavam, preco));
			}
			System.out.println("Carro cadastrado!");
		}else {
			System.out.println("Opção inválida!");
		}
	}
	
	private static void EditarCarro() throws RemoteException {
		System.out.print("Renavam do carro: ");
		String renavam = scan.nextLine();
		
		System.out.println("1. Nome\n" +
				"2. Categoria\n" +
				"3. Ano de fabricação\n" +
				"4. Renavam\n" +
				"5. Preco\n" +
				"Selecione a opção para editar:");
		int opc = scan.nextInt();
		scan.nextLine();
		
		String novoNome = "";
		int novaCategoria = 0;
		String novoAno = "";
		double novoPreco = 0.0;
		
		if(opc==1) {
			System.out.print("Nome: ");
			novoNome = scan.nextLine();
		}
		if(opc==2) {
			System.out.println("1. Categoria Econômica\n" +
					"2. Categoria Intermediária\n" +
					"3. Categoria Executiva\n" +
					"Informe a categoria: ");
			novaCategoria = scan.nextInt();
			scan.nextLine();
		}
		if(opc==3) {
			System.out.print("Novo ano de fabricação: ");
			novoAno = scan.nextLine();
		}
		if(opc==4) {
			System.out.print("Editar renavam: ");
			renavam = scan.nextLine();
		}
		if(opc==5) {
			System.out.print("Novo preço: ");
			novoPreco = scan.nextDouble();
			scan.nextLine();
		}
		if(opc!=1 || opc!=2 || opc!=3) {
			System.out.println("Opção inválida!");
		}
		
		gatewayRemoto.EditarCarro(renavam, new TiposCarros(novoNome, novaCategoria, novoAno, renavam, novoPreco));
				System.out.println("Editado!");
	}
	
	private static void RemoverCarro() throws RemoteException {
			System.out.print("Informe o número do renavam: ");
			String renavamExcluido = scan.nextLine();
			
			gatewayRemoto.RemoverCarro(renavamExcluido);
			System.out.println(renavamExcluido + " removido!");
	}
	
	private static void ComprarCarro() throws RemoteException {
	    System.out.print("Renavam do carro:");
	    String renavam = scan.nextLine();
	    TiposCarros comprar = gatewayRemoto.ConsultarCarro(renavam);
	    
	    if (comprar != null) {
	        System.out.println("Nome: " + comprar.getNome() +
	                "\nCategoria: " + comprar.getTipoCategoria() +
	                "\nAno de fabricação: " + comprar.getAno() +
	                "\nPreço: R$" + comprar.getPreco());
	        System.out.print("Confirmar compra? (S/N)");
	        String confirmar = scan.nextLine();
	        
	        if (confirmar.equals("S") || confirmar.equals("s")) {
	            TiposCarros confirmarCompra = gatewayRemoto.ComprarCarro(renavam);
	            if (confirmarCompra != null) {
	                System.out.println("Compra do(a) carro de renavam " + confirmarCompra.getRenavam() + " efetuada!");
	                Carro.add(confirmarCompra);
	            } else {
	                System.out.println("Carro não disponível! Verifique os outros disponíveis.");
	            }
	        } else {
	            System.out.println("Compra cancelada!");
	        }
	    } else {
	        System.out.println("Carro não encontrado!");
	    }
	}

	
}
