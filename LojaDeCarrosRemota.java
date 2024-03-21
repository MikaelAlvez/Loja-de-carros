package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LojaDeCarrosRemota extends Remote {

	public void AdicionarCarro(TiposCarros Carro) throws RemoteException;
	public void RemoverCarro(String renavam) throws RemoteException;
	
	public List<TiposCarros> ListaDeCarros() throws RemoteException;
	public List<TiposCarros> ListaPorCategoria(int categoria) throws RemoteException;
	
	public TiposCarros ConsultarCarro(String renavam) throws RemoteException;
	
	public void EditarCarro(String renavam, TiposCarros editedCar) throws RemoteException;
	
	public int quantidade(int categoria) throws RemoteException;
	public TiposCarros ComprarCarro(String renavam) throws RemoteException;
	
}

