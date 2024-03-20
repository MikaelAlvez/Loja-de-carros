package LojaDeCarros;

public class CategoriaEconomica extends TiposCarros {
	
	private static final long serialVersionUID = 1L;
	private static int amount;
	
	public CategoriaEconomica(String carro, int categoria, String ano, String renavam, double preco) {
		super(carro, categoria, ano, renavam, preco);
	}

	public static int getAmount() {
		return amount;
	}

	public static void setAmount(int amount) {
		CategoriaEconomica.amount = amount;
	}
	
}
