package LojaDeCarros;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAutenticacao {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4097);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                out.println("Usuário:");
                String usuario = in.readLine();
                out.println("Senha:");
                String senha = in.readLine();
                
                if (usuario.equals("admin") && senha.equals("admin123")) {
                    out.println("Autenticação bem-sucedida!");
                    iniciarServidorLojadeCarros();
                } else {
                    out.println("Usuário ou senha incorretos!");
                }
                
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void iniciarServidorLojadeCarros() {
        try {
            System.out.println("ServidorLojadeCarros iniciado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
