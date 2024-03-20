package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GatewayRemoto extends Remote {

	public void register(Usuarios newUser) throws RemoteException;
	public Usuarios login(String cpf, String password) throws RemoteException;
	
	public void AdicionarCarro(TiposCarros carro) throws RemoteException;
	public void EditarCarro(String renavam, TiposCarros editedCar) throws RemoteException;
	public void RemoverCarro(String renavam) throws RemoteException;
	public void deleteCars(String name) throws RemoteException;
	public List<TiposCarros> listCars() throws RemoteException;
	public List<TiposCarros> listCars(int category) throws RemoteException;
	public TiposCarros searchCar(String renavam) throws RemoteException;
	public List<TiposCarros> searchCars(String category) throws RemoteException;
	public TiposCarros ComprarCarro(String renavam) throws RemoteException;
	public int getAmount(int category) throws RemoteException;
	
}
