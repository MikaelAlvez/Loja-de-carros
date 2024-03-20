package LojaDeCarros;

public class Funcionario extends Usuarios {

	private static final long serialVersionUID = 1L;

	public Funcionario(String newCpf, String newPassword, String newName) {
		super.cpf = newCpf;
		super.password = newPassword;
		super.name = newName;
		super.funcionario = true;
	}
	
	public TiposCarros addCar(TiposCarros newCar) {
		return newCar;
	}
	
	public TiposCarros editCar(TiposCarros car) {
		return car;
	}
	
	public boolean deleteCar(TiposCarros carToDelete) {
		return false;
	}
	
	public boolean deleteCar(long renavamToDelete) {
		return false;
	}
}
