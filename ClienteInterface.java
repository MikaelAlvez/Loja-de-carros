package LojaDeCarros;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClienteInterface extends Remote {
    void notificarAtualizacao(List<Carro> carros) throws RemoteException;
}
