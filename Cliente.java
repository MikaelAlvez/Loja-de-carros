package LojaDeCarros;

public class Cliente extends Usuarios {

	private static final long serialVersionUID = 1L;

	public Cliente(String cpf, String senha, String nome) {
		super.cpf = cpf;
		super.senha = senha;
		super.nome = nome;
		super.funcionario = false;
	}
	
}

