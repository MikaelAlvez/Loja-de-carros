package LojaDeCarros;

import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class ServidorAutenticacao extends UnicastRemoteObject implements AutenticacaoRemota {
    private static final long serialVersionUID = 1L;

	protected ServidorAutenticacao() throws RemoteException {
        super();
    }

    @Override
    public boolean autenticar(String usuario, String senha) throws RemoteException {
        return usuario.equals("admin") && senha.equals("admin123");
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(4097);
            registry.rebind("ServidorAutenticacao", new ServidorAutenticacao());
            System.out.println("Servidor de autenticação iniciado.");
            
            try (Socket gatewaySocket = new Socket("localhost", 4096)) {
				PrintWriter outToGateway = new PrintWriter(gatewaySocket.getOutputStream(), true);
				outToGateway.println("Servidor de Autenticação conectado.");
			}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
