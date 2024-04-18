package LojaDeCarros;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServidorGateway implements GatewayRemoto {

	static String chaveLog = "log";
	static String storageHostName[] = {"loja1","loja2","loja3"};
	static AutenticacaoRemota Autenticar;
	static LojaDeCarrosRemota Loja;
	@SuppressWarnings("exports")
	public static Registry registro;
	
	static List<LojaDeCarrosRemota> lojas;
	
	public static void main(String[] args) throws UnknownHostException {
		
		ServidorGateway gateway = new ServidorGateway();
		lojas = new ArrayList<>();
		
		try {
			Registry autenticar = LocateRegistry.getRegistry("127.0.0.1", 4096);
			Autenticar = (AutenticacaoRemota) autenticar.lookup(chaveLog);
			
			Registry servloja1 = LocateRegistry.getRegistry("localhost", 4097);
			Loja = (LojaDeCarrosRemota) servloja1.lookup(storageHostName[0]);
			
			servloja1 = LocateRegistry.getRegistry("localhost", 5000);
			Loja = (LojaDeCarrosRemota) servloja1.lookup(storageHostName[1]);
			
			servloja1 = LocateRegistry.getRegistry("localhost", 5001);
			Loja = (LojaDeCarrosRemota) servloja1.lookup(storageHostName[2]);
			
			GatewayRemoto ServidorGateway = (GatewayRemoto) UnicastRemoteObject.exportObject(gateway, 0);
			
			LocateRegistry.createRegistry(4098);
			registro = LocateRegistry.getRegistry(4098);
			registro.bind("Gateway", ServidorGateway);
			
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			
			System.out.println("Servidor Gateway iniciado..." +
					"\n[Porta: 4098, IP: 127.0.0.1, Hostname: " + hostname + "]");
			
			System.out.println("\nAguardando conexões...\n");
			
		} catch (RemoteException | AlreadyBoundException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Usuarios login(String usuario, String senha) {
		try {
			Usuarios conectado = Autenticar.login(usuario, senha);
			
			return conectado;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void AdicionarCarro(TiposCarros Carro) throws RemoteException {
		Loja.AdicionarCarro(Carro);
		System.out.println("Carro adicionado!");
	}
	
	@Override
	public void EditarCarro(String renavam, TiposCarros EditarCarro) throws RemoteException {
		Loja.EditarCarro(renavam, EditarCarro);
		System.out.println("Carro de renavam " + renavam + " editado!");
	}

	@Override
	public void RemoverCarro(String renavam) throws RemoteException {
		Loja.RemoverCarro(renavam);
		System.out.println("Carro de renavam " + renavam + " removido!");
	}
	
	@Override
	public List<TiposCarros> ListaDeCarros() throws RemoteException {
		System.out.println("Lista de carros encaminhada.");
		return Loja.ListaDeCarros();
	}

	@Override
	public List<TiposCarros> ListaPorCategoria(int categoria) throws RemoteException {
		System.out.println("Lista de carros da categoria " + categoria + " encaminhada.");
		return Loja.ListaPorCategoria(categoria);
	}
	
	@Override
	public TiposCarros ConsultarCarro(String renavam) throws RemoteException {
		System.out.println("Carro encontrado!");
		return Loja.ConsultarCarro(renavam);
	}
	
	@Override
	public TiposCarros ComprarCarro(String renavam) throws RemoteException {
		System.out.println("Carro de renavam " + renavam + " comprado!");
		return Loja.ComprarCarro(renavam);
	}
	
	@Override
	public int quantidadeCarros(int categoria) throws RemoteException {
		return Loja.quantidade(categoria);
	}
	
}

