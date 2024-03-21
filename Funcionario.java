package LojaDeCarros;

public class Funcionario extends Usuarios {

	private static final long serialVersionUID = 1L;

	public Funcionario(String cpf, String senha, String nome, String login) {
        this.cpf = cpf;
        this.senha = senha;
        this.nome = nome;
        this.login = login;
        this.funcionario = true;
    }
	
	public TiposCarros AdicionarCarro(TiposCarros Carro) {
		return Carro;
	}
	
	public TiposCarros EditarCarro(TiposCarros Carro) {
		return Carro;
	}
	
	public boolean RemoverCarro(long Renavam) {
		return false;
	}
}
