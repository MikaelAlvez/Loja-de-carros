package LojaDeCarros;

public class CategoriaExecutiva extends TiposCarros {

	private static final long serialVersionUID = 1L;
	private static int quant;
	
	public CategoriaExecutiva(String carro, int categoria, String ano, String renavam, double preco) {
		super(carro, categoria, ano, renavam, preco);
	}
	
	public static int getQuant() {
		return quant;
	}

	public static void setQuant(int quant) {
		CategoriaExecutiva.quant = quant;
	}
	
}

