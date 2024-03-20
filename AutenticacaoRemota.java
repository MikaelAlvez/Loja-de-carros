package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutenticacaoRemota extends Remote {

	//void startServer() throws RemoteException;
	Usuarios loginUser(String cpf, String password) throws RemoteException;
	void registerUser(Usuarios newUser) throws RemoteException;
	
}

