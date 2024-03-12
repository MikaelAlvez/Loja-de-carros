package LojaDeCarros;

public class Cliente {
    private String nome;
    private String senha;
    private boolean funcionario;
    
    public Cliente(String nome, String senha, boolean funcionario) {
        this.nome = nome;
        this.senha = senha;
        this.funcionario = funcionario;
    }
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isFuncionario() {
		return funcionario;
	}
	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
	}
}
