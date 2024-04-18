package LojaDeCarros;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ServidorLojaDeCarros implements LojaDeCarrosRemota {
	
	private static TipoReplica tipo;
	private static LojaDeCarrosRemota replica1;
	private static LojaDeCarrosRemota replica2;
	private static BancoDeDadosRemoto banco;

	private static int id;
	
    private static final String LIDER = "lojaLider";
    static String CHAVE_REPLICAS[] = {"loja2", "loja3"};
	static String chaveBanco[] = {"datalider", "data2", "data3"};

    private static HashMap<String, TiposCarros> carros = new HashMap<>();    
    private static int economico = 0, intermediario = 0, executivo = 0; 
    
    public ServidorLojaDeCarros(TipoReplica t) {
    	tipo = t;
	}

    public static void main(String[] args) throws UnknownHostException {
    	ServidorLojaDeCarros servidor = new ServidorLojaDeCarros(TipoReplica.LIDER);
		Scanner scanner = new Scanner(System.in);
		
		try {
			
			LojaDeCarrosRemota server = (LojaDeCarrosRemota) UnicastRemoteObject.exportObject(servidor, 0);
			
			Registry lojaLider = LocateRegistry.createRegistry(4097);
			lojaLider.rebind("lojaLider", server);

			scanner.nextLine();
			
			Registry replica = LocateRegistry.getRegistry(5000);
			replica1 = (LojaDeCarrosRemota) replica.lookup("loja2");
			
			replica = LocateRegistry.getRegistry(5001);
			replica2 = (LojaDeCarrosRemota) replica.lookup("loja3");
			
			Registry base = LocateRegistry.getRegistry(4099);
			banco = (BancoDeDadosRemoto) base.lookup("datalider");

			System.out.println("Servidor de Armazenamento-1 ligado.");

			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void AdicionarCarro(TiposCarros carro) {
    	try {
			if(tipo == TipoReplica.LIDER) {
				banco.AdicionarCarro(carro);
	                System.out.println("Carro adicionado com sucesso.");
			}
    	} catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void EditarCarro(String renavam, TiposCarros carro) {
        try {
			if(tipo == TipoReplica.LIDER) {
	            TiposCarros editCar = banco.ConsultarCarro(renavam);
	
	            if (carro.getNome() != null) {
	                editCar.setNome(carro.getNome());
	            }
	            if (carro.getCategoria() != 0) {
	                switch (editCar.getCategoria()) {
	                    case 1:
	                        economico--;
	                        break;
	                    case 2:
	                        intermediario--;
	                        break;
	                    case 3:
	                        executivo--;
	                        break;
	                }
	                switch (carro.getCategoria()) {
	                    case 1:
	                        economico++;
	                        break;
	                    case 2:
	                        intermediario++;
	                        break;
	                    case 3:
	                        executivo++;
	                        break;
	                }
	                editCar.setCategoria(carro.getCategoria());
	            }
	            if (carro.getAno() != null) {
	                editCar.setAno(carro.getAno());
	            }
	            if (carro.getPreco() != 0.0) {
	                editCar.setPreco(carro.getPreco());
	            }
	
	            banco.EditarCarro(renavam, editCar);
	
	            System.out.println("Carro de renavam " + renavam + " editado!");
	
	            servidor();
			}
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void RemoverCarro(String renavam) {
        try {
			if(tipo == TipoReplica.LIDER) {

	            TiposCarros carro = banco.ConsultarCarro(renavam);
	
	            if (carro != null) {
	                banco.RemoverCarro(renavam);
	                System.out.println("Carro de renavam " + renavam + " removido!");
	            }
	
	            servidor();
			}
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TiposCarros> ListaDeCarros() {
        try {
			if(tipo == TipoReplica.LIDER) {
	
	            List<TiposCarros> listaDeCarros = banco.ListaDeCarros();
	            
	            Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
	            listaDeCarros.sort(comparator);
	            
	            System.out.println("Lista de carros encaminhada!");
	            
	            return banco.ListaDeCarros();
			}
			
            return null;

        } catch (RemoteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    
    @Override
    public List<TiposCarros> ListaPorCategoria(int categoria) {
        try {
			if(tipo == TipoReplica.LIDER) {

	            List<TiposCarros> listaDeCarros = banco.ListaPorCategoria(categoria);
	            
	            Comparator<TiposCarros> comparator = Comparator.comparing(TiposCarros::getNome);
	            listaDeCarros.sort(comparator);
	            
	            System.out.println("Lista de carros " + categoria + " encaminhada!");
	            
	            return banco.ListaPorCategoria(categoria);
			}
			 return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public TiposCarros ConsultarCarro(String renavam) {
        try {
			if(tipo == TipoReplica.LIDER) {

	            TiposCarros localizado = banco.ConsultarCarro(renavam);
	            
	            if (localizado != null) {
	                System.out.println("Carro " + localizado.getNome() + " localizado!");
	            } else {
	                System.out.println("Carro com renavam " + renavam + " não encontrado!");
	            }
	            
	            return localizado;
			}
			return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null; 
        }
    }

    @Override
    public TiposCarros ComprarCarro(String renavam) {
        try {
			if(tipo == TipoReplica.LIDER) {

	            TiposCarros compra = banco.ConsultarCarro(renavam);
	            
	            if (compra != null) {
	                System.out.println("Carro de renavam " + renavam + " comprado!");
	                
	                banco.RemoverCarro(renavam);
	            } else {
	                System.out.println("Carro com renavam " + renavam + " não encontrado para compra!");
	            }
	            
	            return compra;
			}
			return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void servidor() {
        for (TiposCarros carro : carros.values()) {
            switch (carro.getCategoria()) {
                case 1:
                    economico++;
                    break;
                case 2:
                    intermediario++;
                    break;
                case 3:
                    executivo++;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int quantidade(int categoria) throws RemoteException {
        try {
			if(tipo == TipoReplica.LIDER) {
	
	            int quantidadeCategoria = banco.quantidade(categoria);
	
	            return quantidadeCategoria;
			}
			return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
	public TipoReplica getTipo() throws RemoteException {
		return tipo;
	}
    
    @Override
	public void setTipo(TipoReplica tip) throws RemoteException {
		tipo = tip;
	}
    
    @Override
	public int getId() throws RemoteException {
		return id;
	}
    
    @Override
	public void setId(int ID) throws RemoteException {
		id = ID;
	}
    
    @Override
	public void setReplica() throws RemoteException {
    	replica1.setTipo(TipoReplica.SEGUIDOR);
    	replica2.setTipo(TipoReplica.SEGUIDOR);
	}
    
    @Override
	public LojaDeCarrosRemota eleicao() throws RemoteException {
    	replica1.setTipo(TipoReplica.CANDIDATO);
    	replica2.setTipo(TipoReplica.CANDIDATO);

		Random aleat = new Random();
		replica1.setId(aleat.nextInt());
		replica2.setId(aleat.nextInt());

		if(replica1.getId() > replica2.getId()) {
			replica1.setTipo(TipoReplica.LIDER);
			replica1.setReplica();
			return replica1;
		}
		replica2.setTipo(TipoReplica.LIDER);
		replica2.setReplica();
		return replica2;
	}
}
