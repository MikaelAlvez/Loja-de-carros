package LojaDeCarros;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServidorAutenticacaoImpl extends UnicastRemoteObject implements ServidorAutenticacao {
    private static final long serialVersionUID = 1L;
	private Map<String, String> usuarios;

    public ServidorAutenticacaoImpl() throws RemoteException {
        usuarios = new HashMap<>();
        usuarios.put("admin", "admin123");
        usuarios.put("distribuidos", "sd123");
    }

    @Override
    public boolean autenticar(String nomeUsuario, String senha) throws RemoteException {
        String senhaArmazenada = usuarios.get(nomeUsuario);
        return senhaArmazenada != null && senhaArmazenada.equals(senha);
    }
}

