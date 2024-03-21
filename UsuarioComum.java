package LojaDeCarros;

import java.io.Serializable;

public class UsuarioComum extends Usuarios implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    public UsuarioComum(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.funcionario = false;
    }
}
