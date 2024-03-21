package LojaDeCarros;

import java.io.Serializable;

public abstract class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected boolean funcionario;
	protected String nome;
	protected String cpf;
	protected String login;
	protected String senha;
		
	public TiposCarros Consultar(long renavam) {
		return null;
	}
	
	public int getQuant() {
		return 0;
	}
	
	public TiposCarros ComprarCarro(TiposCarros ComprarCarro) {
		return ComprarCarro;
	}
	
	public TiposCarros ComprarCarro(long IDCarro) {
		return null;
	}
	
	public TiposCarros ComprarCarro(String Carro) {
		return null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean funcionario() {
		return funcionario;
	}

	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
