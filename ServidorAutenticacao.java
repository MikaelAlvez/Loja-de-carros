package LojaDeCarros;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map.Entry;

public class ServidorAutenticacao implements AutenticacaoRemota {

	private static String path = "Usuarios.txt";
	private static HashMap<String, Usuarios> users;
	private static ObjectOutputStream fileOutput;
	private static ObjectInputStream fileInput;

	public ServidorAutenticacao() {
		try { // tenta abrir
			fileInput = new ObjectInputStream(new FileInputStream(path));
		} catch (IOException e) { // se nao abrir pq nao existe/funciona
			
			try { // ele faz o output pra poder criar o arquivo certo e dps abre
				fileOutput = new ObjectOutputStream(new FileOutputStream(path));
				fileInput = new ObjectInputStream(new FileInputStream(path));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		users = getFileUsers();

	}

	public static void main(String[] args) {

		ServidorAutenticacao authServer = new ServidorAutenticacao();

		try {
			AutenticacaoRemota server = (AutenticacaoRemota) UnicastRemoteObject.exportObject(authServer, 0);

			LocateRegistry.createRegistry(4097);
			Registry register = LocateRegistry.getRegistry("127.0.0.1", 4097);
			register.bind("Authentication", server);

			System.out.println("Servidor de Autenticação ligado.");

		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}

	}
	
	private void attServer() {

		try {
			fileOutput = new ObjectOutputStream(new FileOutputStream(path));
			
			for (Entry<String, Usuarios> user : users.entrySet()) {
				fileOutput.writeObject(user.getValue());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void registerUser(Usuarios newUser) {
		users = getFileUsers(); // pega do arquivo e bota no mapa
		users.put(newUser.getCpf(), newUser); // add no mapa
		attServer(); // salva o mapa num arquivo
		
		System.out.println("Registrado com sucesso.");
	}

	@Override
	public Usuarios loginUser(String cpf, String password) {

		for (Entry<String, Usuarios> user : users.entrySet()) {
			if (cpf.equals(user.getKey()) && password.equals(user.getValue().getPassword())) {
				System.out.println("Logado com sucesso! Bem-vindo, " + user.getValue().getName() + ".");

				return user.getValue();
			}
		}

		return null;
	}

	private static HashMap<String, Usuarios> getFileUsers() {
		boolean eof = false;
		
		if(users == null) {
			users = new HashMap<String, Usuarios>();
		}

		try {

			while (!eof) {
				Usuarios account = (Usuarios) fileInput.readObject();
				users.put(account.getCpf(), account);
			}

		} catch (IOException e) {
			eof = true;
 		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return users;
	}

}

