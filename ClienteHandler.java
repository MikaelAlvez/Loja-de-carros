package LojaDeCarros;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClienteHandler implements Runnable {
    private Socket clientSocket;

    public ClienteHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

            String mensagemDoCliente = inFromClient.readLine();
            System.out.println("Mensagem recebida do cliente: " + mensagemDoCliente);

            String respostaDoServidor = encaminharSolicitacaoParaServidor(mensagemDoCliente);

            outToClient.println(respostaDoServidor);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String encaminharSolicitacaoParaServidor(String mensagem) {
        return enviarParaServidorAutenticacao(mensagem);
    }

    private String enviarParaServidorAutenticacao(String mensagem) {
        return "Resposta do Servidor de Autenticação: Usuário autenticado com sucesso";
    }
}