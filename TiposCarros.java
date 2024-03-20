package LojaDeCarros;

import java.io.Serializable;

public class TiposCarros implements Serializable {

	private static final long serialVersionUID = 1L;
	private String renavam;
	private String nome;
	private int categoria;
	private String ano;
	private double preco;
	
	public TiposCarros(String nomeCarro, int categoria, String ano, String renavam, double preco) {
		this.nome = nomeCarro;
		this.categoria = categoria;
		this.ano = ano;
		this.renavam = renavam;
		this.preco = preco;
	}
	
	public String getRenavam() {
		return renavam;
	}
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nomeCarro) {
		this.nome = nomeCarro;
	}
	public int getCategoria() {
		return categoria;
	}
	public String getTipoCategoria() {
		switch(categoria) {
		case 1:
			return "Categoria Econômico";
		case 2:
			return "Categoria Intermediária";
		case 3:
			return "Categoria Executiva";
		default:
			return "Tipo desconhecido";
		}
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String manufactureYear) {
		this.ano = manufactureYear;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
}

