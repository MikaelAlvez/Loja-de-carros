package LojaDeCarros;

public class Carro {
	private String renavan;
    private String nome;
    private CategoriaCarro categoria; // Alteração aqui
    private int anoFabricacao;
    private int quantidadeDisponivel;
    private double preco;
    
    public enum CategoriaCarro {
        ECONOMICO,
        INTERMEDIARIO,
        EXECUTIVO
    }
    
    public Carro(String renavan, String nome, CategoriaCarro categoria, int anoFabricacao, int quantidadeDisponivel, double preco) {
        this.renavan = renavan;
        this.nome = nome;
        this.setCategoria(categoria);
        this.anoFabricacao = anoFabricacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.preco = preco;
    }
    
	public String getRenavan() {
		return renavan;
	}
	public void setRenavan(String renavan) {
		this.renavan = renavan;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}
	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}

	public CategoriaCarro getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCarro categoria) {
		this.categoria = categoria;
	}
}
