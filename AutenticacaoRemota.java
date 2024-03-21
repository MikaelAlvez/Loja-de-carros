package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AutenticacaoRemota extends Remote {
    Usuarios login(String usuario, String senha) throws RemoteException;   
}
