package LojaDeCarros;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServidorImpl extends UnicastRemoteObject implements Servidor {
    private static final long serialVersionUID = 1L;
	private List<Carro> carros;

    public ServidorImpl() throws RemoteException {
        carros = new ArrayList<>();
    }

    @Override
    public List<Carro> listarCarros() throws RemoteException {
        return carros;
    }
    
    @Override
    public void adicionarCarro(Carro carro) throws RemoteException {
        carros.add(carro);
    }
    
    public void apagarCarro(String nomeCarro) throws RemoteException {
        Iterator<Carro> iterator = carros.iterator();
        while (iterator.hasNext()) {
            Carro carro = iterator.next();
            if (carro.getNome().equalsIgnoreCase(nomeCarro) || carro.getQuantidadeDisponivel() == 0) {
                iterator.remove();
            }
        }
    }
    
    
    @Override
    public Carro pesquisarCarro(String chave) throws RemoteException {
    	for (Carro carro : carros) {
            if (carro.getRenavan().equals(chave) || carro.getNome().equalsIgnoreCase(chave)) {
                return carro;
            }
        }
        return null;   
    }

    @Override
    public boolean comprarCarro(String renavan) throws RemoteException {
    	 for (Carro carro : carros) {
             if (carro.getRenavan().equals(renavan)) {
                 if (carro.getQuantidadeDisponivel() > 0) {
                     carro.setQuantidadeDisponivel(carro.getQuantidadeDisponivel() - 1);
                     return true;
                 } else {
                     return false;
                 }
             }
         }
         return false; 
    }
}
