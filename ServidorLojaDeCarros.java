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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class ServidorLojaDeCarros implements LojaDeCarrosRemota {

	private String path = "Carros.txt";
	private static HashMap<String, TiposCarros> cars;
	private static ObjectOutputStream fileOutput;
	private static ObjectInputStream fileInput;
	private static int economicAmount = 0, intermediaryAmount = 0, executiveAmount = 0; 
	
	public ServidorLojaDeCarros() {
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
		
		cars = getFileCars();
	}
	
	public static void main(String[] args) {
		
		ServidorLojaDeCarros storServer = new ServidorLojaDeCarros();

		try {
			LojaDeCarrosRemota server = (LojaDeCarrosRemota) UnicastRemoteObject.exportObject(storServer, 0);

			LocateRegistry.createRegistry(5002);
			Registry register = LocateRegistry.getRegistry("127.0.0.2", 4098);
			register.bind("Storage", server);

			System.out.println("Servidor de Armazenamento ligado.");

		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void addCar(TiposCarros newCar) {
		cars = getFileCars(); // pega do arquivo e bota no mapa
		cars.put(newCar.getRenavam(), newCar); // add no mapa
		attServer(); // salva o mapa num arquivo
		
		System.out.println("Carro adicionado com sucesso.");
	}

	@Override
	public void editCar(String renavam, TiposCarros editedCar) {
		cars = getFileCars(); // att o mapa pra versao mais recente
		TiposCarros editCar = searchCar(renavam);
		
		if(editedCar.getNome() != null) {
			editCar.setNome(editedCar.getNome());
		}
		if(editedCar.getCategoria() != 0) {
			switch(editCar.getCategoria()) {
			case 1:
				economicAmount--;
				break;
			case 2:
				intermediaryAmount--;
				break;
			case 3:
				executiveAmount--;
				break;
			}
			switch(editedCar.getCategoria()) {
			case 1:
				economicAmount++;
				break;
			case 2:
				intermediaryAmount++;
				break;
			case 3:
				executiveAmount++;
				break;
			}
			editCar.setCategoria(editedCar.getCategoria());
		}
		if(editedCar.getAno() != null) {
			editCar.setAno(editedCar.getAno());
		}
		if(editedCar.getPreco() != 0.0) {
			editCar.setPreco(editedCar.getPreco());
		}
		
		System.out.println("Carro de renavam " + renavam + " editado com sucesso.");
		
		attServer(); // salva o mapa num arquivo
	}

	@Override
	public void deleteCar(String renavam) {
		cars = getFileCars(); // att o mapa pra versao mais recente
		TiposCarros deleteCar = searchCar(renavam);
		
		if(deleteCar != null) {
			cars.remove(renavam, deleteCar);
			System.out.println("Carro de renavam " + renavam + " deletado com sucesso.");
		}
		
		attServer(); // salva o mapa num arquivo
	}
	
	@Override
	public void deleteCars(String name) {
		cars = getFileCars(); // att o mapa pra versao mais recente
		List<TiposCarros> deleteCars = searchCars(name);
		
		if(deleteCars != null) {
			for(TiposCarros toDeleteCar : deleteCars) {
				cars.remove(toDeleteCar.getRenavam(), toDeleteCar);
			}
			System.out.println("Todos os carros " + name + " deletados com sucesso.");
		}
		
		attServer();
	}

	@Override
	public List<TiposCarros> listCars() {
		cars = getFileCars();
		List<TiposCarros> list = new ArrayList<TiposCarros>();
		
		for (Entry<String, TiposCarros> car : cars.entrySet()) {
			list.add(car.getValue());
		}
		
		Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
		Collections.sort(list, comparator);
		
		System.out.println("Lista de carros enviada.");
		
		return list;
	}
	
	@Override
	public List<TiposCarros> listCars(int category) {
		cars = getFileCars();
		List<TiposCarros> list = new ArrayList<TiposCarros>();
		
		for (Entry<String, TiposCarros> car : cars.entrySet()) {
			if(car.getValue().getCategoria() == category) {
				list.add(car.getValue());	
			}
		}
		
		Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
		Collections.sort(list, comparator);
				
		System.out.println("Lista de carros da categoria " + category + " enviada.");
		
		return list;
	}

	@Override
	public TiposCarros searchCar(String renavam) {
		cars = getFileCars();
		
		TiposCarros finded = null;
		for (Entry<String, TiposCarros> car : cars.entrySet()) {
			if (renavam.equals(car.getKey()) && renavam.equals(car.getValue().getRenavam())) {
				System.out.println("Carro encontrado com sucesso! Nome: " + car.getValue().getNome() + ".");
				finded = car.getValue();
				break;
			}
		}
		
		return finded;

	}

	@Override
	public List<TiposCarros> searchCars(String name) {
		cars = getFileCars();
		
		List<TiposCarros> findeds = new ArrayList<TiposCarros>();
		for (Entry<String, TiposCarros> car : cars.entrySet()) {
			if (name.equalsIgnoreCase(car.getValue().getNome())) {
				System.out.println("Renavam: " + car.getValue().getRenavam() + ".");
				findeds.add(car.getValue());
			}
		}
		
		return findeds;
	}

	@Override
	public TiposCarros buyCar(String renavam) {
		
		TiposCarros purchased = searchCar(renavam);
		System.out.println("Carro de renavam " + renavam + " foi comprado.");
		deleteCar(renavam);
		
		return purchased;
	}
	
	private static HashMap<String, TiposCarros> getFileCars() {
		boolean eof = false;
		
		if(cars == null) {
			cars = new HashMap<String, TiposCarros>();
		}
		
		try {
			while (!eof) {
				TiposCarros registredCar = (TiposCarros) fileInput.readObject();
				cars.put(registredCar.getRenavam(), registredCar);
			}

		} catch (IOException e) {
			eof = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return cars;
	}
	
	private void attServer() {

		try {
			fileOutput = new ObjectOutputStream(new FileOutputStream(path));
			
			economicAmount = 0;
			intermediaryAmount = 0;
			executiveAmount = 0;
			for (Entry<String, TiposCarros> TiposCarros : cars.entrySet()) {
				fileOutput.writeObject(TiposCarros.getValue());
				
				switch(TiposCarros.getValue().getCategoria()) {
				case 1:
					economicAmount++;
					break;
				case 2:
					intermediaryAmount++;
					break;
				case 3:
					executiveAmount++;
					break;
				default:
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getAmount(int category) throws RemoteException {
		cars = getFileCars();
		attServer();
		
		switch(category) {
		case 1:
			CategoriaEconomica.setAmount(economicAmount);
			return economicAmount;
		case 2:
			CategoriaIntermediaria.setAmount(intermediaryAmount);
			return intermediaryAmount;
		case 3:
			CategoriaExecutiva.setAmount(executiveAmount);
			return executiveAmount;
		default:
			CategoriaEconomica.setAmount(economicAmount);
			CategoriaIntermediaria.setAmount(intermediaryAmount);
			CategoriaExecutiva.setAmount(executiveAmount);
			return economicAmount + intermediaryAmount + executiveAmount;	
		}
	}
	
}
