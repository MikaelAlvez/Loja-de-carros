package LojaDeCarros;

public class Cliente extends Usuarios {

	private static final long serialVersionUID = 1L;

	public Cliente(String newCpf, String newPassword, String newName) {
		super.cpf = newCpf;
		super.password = newPassword;
		super.name = newName;
		super.funcionario = false;
	}
	
}

