package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LojaDeCarrosRemota extends Remote {

	public void addCar(TiposCarros newCar) throws RemoteException;
	public void editCar(String renavam, TiposCarros editedCar) throws RemoteException;
	public void deleteCar(String renavam) throws RemoteException;
	public void deleteCars(String name) throws RemoteException;
	public List<TiposCarros> listCars() throws RemoteException;
	public List<TiposCarros> listCars(int category) throws RemoteException;
	public TiposCarros searchCar(String renavam) throws RemoteException;
	public List<TiposCarros> searchCars(String category) throws RemoteException;
	public TiposCarros buyCar(String renavam) throws RemoteException;
	public int getAmount(int category) throws RemoteException;
	
}

