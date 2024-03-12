package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Servidor extends Remote {
    List<Carro> listarCarros() throws RemoteException;
    Carro pesquisarCarro(String chave) throws RemoteException;
    boolean comprarCarro(String renavan) throws RemoteException;
	void adicionarCarro(Carro carro) throws RemoteException;
}
