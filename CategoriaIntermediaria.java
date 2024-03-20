package LojaDeCarros;

public class CategoriaIntermediaria extends TiposCarros {

	private static final long serialVersionUID = 1L;
	private static int amount;
	
	public CategoriaIntermediaria(String carro, int categoria, String ano, String renavam, double preco) {
		super(carro, categoria, ano, renavam, preco);
	}
	
	public static int getAmount() {
		return amount;
	}

	public static void setAmount(int amount) {
		CategoriaIntermediaria.amount = amount;
	}
	
}

