package LojaDeCarros;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class ServidorLojaDeCarros implements LojaDeCarrosRemota {
	
	static String chaveBanco[] = {"datalider", "data2", "data3"};
	static BancoDeDadosRemoto Banco;

    private static HashMap<String, TiposCarros> carros = new HashMap<>();	
	private static int economico = 0, intermediario = 0, executivo = 0; 
	
	public static void main(String[] args) throws UnknownHostException {
		
		ServidorLojaDeCarros BancoDeDados = new ServidorLojaDeCarros();

		try {
			LojaDeCarrosRemota ServidorLoja = (LojaDeCarrosRemota) UnicastRemoteObject.exportObject(BancoDeDados, 0);
			
			// Replica 1
			LocateRegistry.createRegistry(4097);
			Registry register = LocateRegistry.getRegistry("127.0.0.2", 4097);
			register.bind("lojalider", ServidorLoja);

			// Replica 2
			Registry register2 = LocateRegistry.getRegistry("127.0.0.2", 5000);
			register2.bind("loja2", ServidorLoja);
			
			// Replica 3
			register2 = LocateRegistry.getRegistry("127.0.0.2", 5001);
			register2.bind("loja3", ServidorLoja);
			
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			
			System.out.println("Servidor loja de carro iniciado..." +
					"\n[Porta: 4097, Endereço IP: 127.0.0.2, HostName: " + hostname + "]\n");
			
			System.out.println("\nAguardando conexões...\n");
			
			Registry banco = LocateRegistry.getRegistry("127.0.0.3", 4099);
			Banco = (BancoDeDadosRemoto) banco.lookup(chaveBanco[0]);
			
			System.out.println("Banco de dados conectado.");
			
		} catch (RemoteException | AlreadyBoundException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void AdicionarCarro(TiposCarros carro) {
	    try {
	        Banco.AdicionarCarro(carro);
	        System.out.println("Carro adicionado com sucesso.");
	    } catch (RemoteException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void EditarCarro(String renavam, TiposCarros carro) {
	    try {
	        TiposCarros editCar = Banco.ConsultarCarro(renavam);

	        if (carro.getNome() != null) {
	            editCar.setNome(carro.getNome());
	        }
	        if (carro.getCategoria() != 0) {
	            switch (editCar.getCategoria()) {
	                case 1:
	                    economico--;
	                    break;
	                case 2:
	                    intermediario--;
	                    break;
	                case 3:
	                    executivo--;
	                    break;
	            }
	            switch (carro.getCategoria()) {
	                case 1:
	                    economico++;
	                    break;
	                case 2:
	                    intermediario++;
	                    break;
	                case 3:
	                    executivo++;
	                    break;
	            }
	            editCar.setCategoria(carro.getCategoria());
	        }
	        if (carro.getAno() != null) {
	            editCar.setAno(carro.getAno());
	        }
	        if (carro.getPreco() != 0.0) {
	            editCar.setPreco(carro.getPreco());
	        }

	        Banco.EditarCarro(renavam, editCar);

	        System.out.println("Carro de renavam " + renavam + " editado!");

	        servidor();
	    } catch (RemoteException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void RemoverCarro(String renavam) {
	    try {
	        TiposCarros carro = Banco.ConsultarCarro(renavam);

	        if (carro != null) {
	            Banco.RemoverCarro(renavam);
	            System.out.println("Carro de renavam " + renavam + " removido!");
	        }

	        servidor();
	    } catch (RemoteException e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public List<TiposCarros> ListaDeCarros() {
	    try {
	        List<TiposCarros> listaDeCarros = Banco.ListaDeCarros();
	        
	        Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
	        listaDeCarros.sort(comparator);
	        
	        System.out.println("Lista de carros encaminhada!");
	        
	        return listaDeCarros;
	    } catch (RemoteException e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}

	
	@Override
	public List<TiposCarros> ListaPorCategoria(int categoria) {
	    try {
	        List<TiposCarros> listaDeCarros = Banco.ListaPorCategoria(categoria);
	        
	        Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
	        listaDeCarros.sort(comparator);
	        
	        System.out.println("Lista de carros " + categoria + " encaminhada!");
	        
	        return listaDeCarros;
	    } catch (RemoteException e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}


	@Override
	public TiposCarros ConsultarCarro(String renavam) {
	    try {
	        TiposCarros localizado = Banco.ConsultarCarro(renavam);
	        
	        if (localizado != null) {
	            System.out.println("Carro " + localizado.getNome() + " localizado!");
	        } else {
	            System.out.println("Carro com renavam " + renavam + " não encontrado!");
	        }
	        
	        return localizado;
	    } catch (RemoteException e) {
	        e.printStackTrace();
	        return null; 
	    }
	}


	@Override
	public TiposCarros ComprarCarro(String renavam) {
	    try {
	        TiposCarros compra = Banco.ConsultarCarro(renavam);
	        
	        if (compra != null) {
	            System.out.println("Carro de renavam " + renavam + " comprado!");
	            
	            Banco.RemoverCarro(renavam);
	        } else {
	            System.out.println("Carro com renavam " + renavam + " não encontrado para compra!");
	        }
	        
	        return compra;
	    } catch (RemoteException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	private void servidor() {
		for (TiposCarros carro : carros.values()) {
            switch (carro.getCategoria()) {
                case 1:
                    economico++;
                    break;
                case 2:
                    intermediario++;
                    break;
                case 3:
                    executivo++;
                    break;
                default:
                    break;
            }
        }
	}

	@Override
	public int quantidade(int categoria) throws RemoteException {
	    try {
	        int quantidadeCategoria = Banco.quantidade(categoria);

	        return quantidadeCategoria;
	    } catch (RemoteException e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	
}
