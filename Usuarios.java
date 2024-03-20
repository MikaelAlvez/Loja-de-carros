package LojaDeCarros;

import java.io.Serializable;

public abstract class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String name;
	protected String cpf;
	protected boolean funcionario;
	protected String password;
	
	public void list() {}
	
	public TiposCarros search(long renavam) {
		return null;
	}
	
	public int getAmount() {
		return 0;
	}
	
	public TiposCarros buyCar(TiposCarros carToBuy) {
		return carToBuy;
	}
	
	public TiposCarros buyCar(long carIdToBuy) {
		return null;
	}
	
	public TiposCarros buyCar(String nameCarToBuy) {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
