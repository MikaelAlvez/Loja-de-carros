package LojaDeCarros;

public class CategoriaEconomica extends TiposCarros {
	
	private static final long serialVersionUID = 1L;
	private static int quant;
	
	public CategoriaEconomica(String carro, int categoria, String ano, String renavam, double preco) {
		super(carro, categoria, ano, renavam, preco);
	}

	public static int Quant() {
		return quant;
	}

	public static void setQuant(int quant) {
		CategoriaEconomica.quant = quant;
	}
	
}
