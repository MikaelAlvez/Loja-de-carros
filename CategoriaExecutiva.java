package LojaDeCarros;

public class CategoriaExecutiva extends TiposCarros {

	private static final long serialVersionUID = 1L;
	private static int amount;
	
	public CategoriaExecutiva(String carro, int categoria, String ano, String renavam, double preco) {
		super(carro, categoria, ano, renavam, preco);
	}
	
	public static int getAmount() {
		return amount;
	}

	public static void setAmount(int amount) {
		CategoriaExecutiva.amount = amount;
	}
	
}

