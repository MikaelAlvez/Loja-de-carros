package LojaDeCarros;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteHandler {
	
	private static boolean conexao = false;
	private static Scanner scan;
	private static GatewayRemoto gatewayRemoto;
	private static Usuarios usuario;
	private static List<TiposCarros> Carro;
	
	public static void main(String[] args) {
		
		scan = new Scanner(System.in);
		Carro = new ArrayList<TiposCarros>();
		
		try {
			Registry authRegister = LocateRegistry.getRegistry(4096);
			gatewayRemoto = (GatewayRemoto) authRegister.lookup("Gateway");
			
			usuario = login();
					if(usuario != null) {
						conexao = true;
					}
				
			while(conexao) {
				if(usuario.funcionario()) {
				System.out.println("1. Listar carros\n" +
						"2. Consultar carros\n" +
						"3. Realizar compra\n" +
						"4. Quantidade de carros disponíveis\n" +
						"5. Cadastrar carro\n" +
						"6. Editar carro\n" +
						"7. Remover carro\n" +
						"8. Carros Vendidos");
				}else {
					System.out.println("1. Listar carros\n" +
							"2. Consultar carros\n" +
							"3. Realizar compra\n" +
							"4. Quantidade de carros disponíveis\n");
					}
				System.out.print("Opção: ");
				int opc = scan.nextInt();
				scan.nextLine();
				
				switch(opc) {
				case 1:
					List<TiposCarros> carrosRegistrados = new ArrayList<TiposCarros>();
					
					carrosRegistrados = gatewayRemoto.listCars();
					System.out.println("\nListar por Categoria: "+
						"1. Categoria Economica\n" + 
						"2. Categoria Intermediária\n" +
						"3. Categoria Executiva");
					System.out.print("Opção: ");
					int categoria = scan.nextInt();
					scan.nextLine();
					
					if(categoria >=1 && categoria<=3) {
						carrosRegistrados = gatewayRemoto.listCars(categoria);
						for(TiposCarros carro : carrosRegistrados) {
							System.out.println("\nNome: " + carro.getNome() +
									"Categoria: " + carro.getTipoCategoria() +
									"Ano de fabricação: " + carro.getAno() +
									"Renavam: " + carro.getRenavam() +
									"Preço: R$" + carro.getPreco());
						}
					}else {
						System.out.println("Opção inválida!");
					}
					break;
					
				case 2:
					System.out.println("Informe o número do Renavam");
					String renavam = scan.nextLine();
					scan.nextLine();
		
						TiposCarros consultarCarro = gatewayRemoto.searchCar(renavam);
						if(consultarCarro != null) {
							System.out.println("\nNome: " + consultarCarro.getNome() +
								"Categoria: " + consultarCarro.getTipoCategoria() +
								"Ano de fabricação: " + consultarCarro.getAno() +
								"Renavam: " + consultarCarro.getRenavam() +
								"Preço: R$" + consultarCarro.getPreco());
						} else {
							System.out.println("Carro não encontrado.");
						}
					break;
				case 3:
					ComprarCarro();
					break;
				case 4:
					int total = gatewayRemoto.getAmount(1) + gatewayRemoto.getAmount(2) + gatewayRemoto.getAmount(3);
					System.out.println("Quantidade total de carros disponíveis: " + total);
					break;
				case 5:
					if(usuario.funcionario()) {
						AcidionarCarro();
					}
					break;
				case 6:
					if(usuario.funcionario()) {
						EditarCarro();
					}
					break;
				case 7:
					if(usuario.funcionario()) {
						RemoverCarro();
					}
					break;
				case 8:
					for(TiposCarros vendidos : Carro) {
						System.out.println("Nome: " + vendidos.getNome() +
							"Categoria: " + vendidos.getTipoCategoria() +
							"Ano de fabricação: " + vendidos.getAno() +
							"Renavam: " + vendidos.getRenavam() +
							"Preço: R$" + vendidos.getPreco());
					}
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
		System.out.print("CPF: ");
		String cpf = scan.nextLine();
		System.out.print("Senha: ");
		String password = scan.nextLine();
		
		Usuarios connected = gatewayRemoto.login(cpf, password);
		
		return connected;
		
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
				"3. Ano de fabricação" +
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
		TiposCarros comprar = gatewayRemoto.searchCar(renavam);
		
		System.out.println("Nome: " + comprar.getNome() +
				"Categoria: " + comprar.getTipoCategoria() +
				"Ano de fabricação: " + comprar.getAno() +
				"Preço: R$" + comprar.getPreco());
		System.out.print("Confirmar compra? (S/N)");
		String confirmar = scan.nextLine();
		
		if(confirmar=="S" || confirmar=="s") {
			TiposCarros confirmarCompra = gatewayRemoto.ComprarCarro(renavam);
			if(confirmarCompra != null) {
				System.out.println("Compra do(a) " +confirmarCompra.getRenavam() + " efetuada!");
				Carro.add(confirmarCompra);
			} else {
				System.out.println("Carro não disponível! Verifique os outros disponíveis.");
			}
		}else {
			System.out.println("Compra cancelada!");
		}
	}
	
}
