package LojaDeCarros;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
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

public class ServidorBancoDeDados implements BancoDeDadosRemoto{
	
	private static HashMap<String, TiposCarros> carros = new HashMap<>();	
	private static int economico = 0, intermediario = 0, executivo = 0; 
	
	public ServidorBancoDeDados() {
		carros.put("123457", new CategoriaEconomica("Gol", 1, "2022", "123457", 40000.0));
		carros.put("123456", new CategoriaEconomica("Fiat Uno", 1, "2022", "123456", 25000.0));
	    carros.put("789012", new CategoriaIntermediaria("Civic", 2, "2023", "789012", 80000.0));
	    carros.put("234567", new CategoriaIntermediaria("Volkswagen Gol", 2, "2021", "234567", 35000.0));
	    carros.put("345678", new CategoriaExecutiva("BMW", 3, "2021", "345678", 150000.0));
	    carros.put("345679", new CategoriaExecutiva("Toyota Corolla", 3, "2023", "345679", 80000.0));
	    
	    servidor();
	}
	
	public static void main(String[] args) throws UnknownHostException {
		
		ServidorBancoDeDados bancoDeDados = new ServidorBancoDeDados();

		try {
		    BancoDeDadosRemoto banco = (BancoDeDadosRemoto) UnicastRemoteObject.exportObject(bancoDeDados, 0);

		    LocateRegistry.createRegistry(4099);
		    Registry register = LocateRegistry.getRegistry("127.0.0.3", 4099);
		    register.bind("data", banco);

		    String hostname = java.net.InetAddress.getLocalHost().getHostName();

		    System.out.println("Banco de dados iniciado..." +
		            "\n[Porta: 4099, Endereço IP: 127.0.0.3, HostName: " + hostname + "]\n");

		    System.out.println("\nAguardando conexões...\n");

		} catch (RemoteException | AlreadyBoundException e) {
		    e.printStackTrace();
		}
	}
	
	@Override
	public void EditarCarro(String renavam, TiposCarros carro) {
		TiposCarros editCar = ConsultarCarro(renavam);
		
		if(carro.getNome() != null) {
			editCar.setNome(carro.getNome());
		}
		if(carro.getCategoria() != 0) {
			switch(editCar.getCategoria()) {
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
			switch(carro.getCategoria()) {
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
		if(carro.getAno() != null) {
			editCar.setAno(carro.getAno());
		}
		if(carro.getPreco() != 0.0) {
			editCar.setPreco(carro.getPreco());
		}
		
		System.out.println("Carro de renavam " + renavam + " editado!");
		
		servidor();
	}
	
	@Override
	public void AdicionarCarro(TiposCarros carro) {
		carros.put(carro.getRenavam(), carro);
		servidor();
		
		System.out.println("Carro adicionado com sucesso.");
	}

	@Override
	public void RemoverCarro(String renavam) {
		TiposCarros carro = ConsultarCarro(renavam);
		
		if(carro != null) {
			carros.remove(renavam, carro);
			System.out.println("Carro de renavam " + renavam + " removido!");
		}
		
		servidor();
	}

	@Override
	public List<TiposCarros> ListaDeCarros() {
		List<TiposCarros> listaDeCarros = new ArrayList<TiposCarros>();
		
		for (Entry<String, TiposCarros> carro : carros.entrySet()) {
			listaDeCarros.add(carro.getValue());
		}
		
		Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
		Collections.sort(listaDeCarros, comparator);
		
		System.out.println("Lista de carros encaminhada!");
		
		return listaDeCarros;
	}
	
	@Override
	public List<TiposCarros> ListaPorCategoria(int categoria) {
		List<TiposCarros> listaDeCarros = new ArrayList<TiposCarros>();
		
		for (Entry<String, TiposCarros> carro : carros.entrySet()) {
			if(carro.getValue().getCategoria() == categoria) {
				listaDeCarros.add(carro.getValue());	
			}
		}
		
		Comparator<TiposCarros> verifica = Comparator.comparing(TiposCarros::getNome);
		Collections.sort(listaDeCarros, verifica);
				
		System.out.println("Lista de carros " + categoria + " encaminhada!");
		
		return listaDeCarros;
	}

	@Override
	public TiposCarros ConsultarCarro(String renavam) {
		
		TiposCarros localizado = null;
		for (Entry<String, TiposCarros> carro : carros.entrySet()) {
			if (renavam.equals(carro.getKey()) && renavam.equals(carro.getValue().getRenavam())) {
				System.out.println("Carro" + carro.getValue().getNome() + " localizado!");
				localizado = carro.getValue();
				break;
			}
		}	
		return localizado;
	}

	@Override
	public TiposCarros ComprarCarro(String renavam) {
		
		TiposCarros compra = ConsultarCarro(renavam);
		System.out.println("Carro de renavam " + renavam + " comprado!");
		RemoverCarro(renavam);
		
		return compra;
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
    public void atualizarContagemCategorias() throws RemoteException {
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
		servidor();
		
		switch(categoria) {
		case 1:
			CategoriaEconomica.setQuant(economico);
			return economico;
		case 2:
			CategoriaIntermediaria.setQuant(intermediario);
			return intermediario;
		case 3:
			CategoriaExecutiva.setQuant(executivo);
			return executivo;
		default:
			CategoriaEconomica.setQuant(economico);
			CategoriaIntermediaria.setQuant(intermediario);
			CategoriaExecutiva.setQuant(executivo);
			return economico + intermediario + executivo;	
		}
	}
}
