package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorAutenticacao extends Remote {
    boolean autenticar(String nomeUsuario, String senha) throws RemoteException;
}

