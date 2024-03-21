package LojaDeCarros;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map.Entry;

public class ServidorAutenticacao implements AutenticacaoRemota {

	private static HashMap<String, Usuarios> usuarios;

	public ServidorAutenticacao() {
	    usuarios = new HashMap<>();
	    usuarios.put("joao", new UsuarioComum("João Silva", "joao", "senha123"));
	    usuarios.put("maria", new UsuarioComum("Maria Santos", "maria", "123456"));
	    usuarios.put("carlos", new UsuarioComum("Carlos Oliveira", "carlos", "senha1234"));
	    
	    usuarios.put("ana", new Funcionario("123456789", "senha123", "Ana Maria", "ana"));
        usuarios.put("pedro", new Funcionario("987654321", "senha456", "Pedro Souza", "pedro"));
        usuarios.put("funcionario", new Funcionario("456789123", "senha789", "Funcionário loja", "funcionario"));
        
	}


	public static void main(String[] args) throws UnknownHostException {

		ServidorAutenticacao AutenticaUsuario = new ServidorAutenticacao();

		try {
			AutenticacaoRemota server = (AutenticacaoRemota) UnicastRemoteObject.exportObject(AutenticaUsuario, 0);
			
			LocateRegistry.createRegistry(4096);
			Registry registro = LocateRegistry.getRegistry("127.0.0.1", 4096);
			registro.bind("log", server);

			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			
			System.out.println("Servidor de Autenticação iniciado!\n"+
		    	"[Porta: 4096, IP: 127.0.0.1, Hostname: " + hostname + "]");
			
			System.out.println("\nAguardando conexões...\n");

		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public Usuarios login(String usuario, String senha) {

		for (Entry<String, Usuarios> user : usuarios.entrySet()) {
			if (usuario.equals(user.getKey()) && senha.equals(user.getValue().getSenha())) {
				System.out.println("Logado com sucesso! Bem-vindo, " + user.getValue().getNome() + ".");

				return user.getValue();
			}
		}

		return null;
	}

}

