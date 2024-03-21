package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GatewayRemoto extends Remote {

	public Usuarios login(String usuario, String senha) throws RemoteException;
	
	public void AdicionarCarro(TiposCarros carro) throws RemoteException;
	public void RemoverCarro(String renavam) throws RemoteException;
	
	public List<TiposCarros> ListaDeCarros() throws RemoteException;
	public List<TiposCarros> ListaPorCategoria(int categoria) throws RemoteException;
	
	public TiposCarros ConsultarCarro(String renavam) throws RemoteException;
	
	public void EditarCarro(String renavam, TiposCarros editedCar) throws RemoteException;
	
	public int quantidadeCarros(int categoria) throws RemoteException;
	public TiposCarros ComprarCarro(String renavam) throws RemoteException;
	
}
