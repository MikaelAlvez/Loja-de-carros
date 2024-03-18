package LojaDeCarros;

import java.rmi.*;

public interface AutenticacaoRemota extends Remote {
    boolean autenticar(String usuario, String senha) throws RemoteException;
}
