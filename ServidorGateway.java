package LojaDeCarros;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServidorGateway implements GatewayRemoto {

	static String authenticationHostName = "Authentication";
	static String storageHostName = "Storage";
	static AutenticacaoRemota authServer;
	static LojaDeCarrosRemota storServer;
	@SuppressWarnings("exports")
	public static Registry register;
	
	public static void main(String[] args) {
		
		ServidorGateway gateway = new ServidorGateway();
		
		try {
			Registry authRegister = LocateRegistry.getRegistry("127.0.0.1", 4097);
			authServer = (AutenticacaoRemota) authRegister.lookup(authenticationHostName);
			
			Registry stgRegister = LocateRegistry.getRegistry("127.0.0.2", 4098);
			storServer = (LojaDeCarrosRemota) stgRegister.lookup(storageHostName);
			
			GatewayRemoto protocol = (GatewayRemoto) UnicastRemoteObject.exportObject(gateway, 0);
			
			LocateRegistry.createRegistry(4099);
			register = LocateRegistry.getRegistry(4099);
			register.bind("Gateway", protocol);
			
			System.out.println("Gateway ligado...");
			
		} catch (RemoteException | AlreadyBoundException | NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void register(Usuarios newUser) {
		try {
			authServer.registerUser(newUser);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Usuarios login(String cpf, String password) {
		try {
			Usuarios connected = authServer.loginUser(cpf, password);
			
			return connected;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void AdicionarCarro(TiposCarros newCar) throws RemoteException {
		storServer.addCar(newCar);
		System.out.println("Carro adicionado com sucesso.");
	}
	
	@Override
	public void EditarCarro(String renavam, TiposCarros editedCar) throws RemoteException {
		storServer.editCar(renavam, editedCar);
		System.out.println("Carro de renavam " + renavam + " editado com sucesso.");
	}

	@Override
	public void RemoverCarro(String renavam) throws RemoteException {
		storServer.deleteCar(renavam);
		System.out.println("Carro de renavam " + renavam + " deletado com sucesso.");
	}
	
	@Override
	public void deleteCars(String name) throws RemoteException {
		storServer.deleteCars(name);
		System.out.println("Todos os carros " + name + " deletados com sucesso.");
	}
	
	@Override
	public List<TiposCarros> listCars() throws RemoteException {
		System.out.println("Lista de carros enviada.");
		return storServer.listCars();
	}

	@Override
	public List<TiposCarros> listCars(int category) throws RemoteException {
		System.out.println("Lista de carros da categoria " + category + " enviada.");
		return storServer.listCars(category);
	}
	
	@Override
	public TiposCarros searchCar(String renavam) throws RemoteException {
		System.out.println("Carro encontrado com sucesso!");
		return storServer.searchCar(renavam);
	}

	@Override
	public List<TiposCarros> searchCars(String name) throws RemoteException {
		System.out.println("Lista de carros encontrada com sucesso!");
		return storServer.searchCars(name);
	}
	
	@Override
	public TiposCarros ComprarCarro(String renavam) throws RemoteException {
		System.out.println("Carro de renavam " + renavam + " foi comprado.");
		return storServer.buyCar(renavam);
	}
	
	@Override
	public int getAmount(int category) throws RemoteException {
		return storServer.getAmount(category);
	}
	
}

