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
        // Lógica para decidir qual servidor processará a mensagem
        // Por exemplo, você pode verificar o tipo de mensagem e encaminhar para o servidor apropriado
        // Aqui, para fins de exemplo, sempre encaminhamos para o ServidorAutenticacao
        // Você precisará implementar essa lógica com base nos requisitos do seu sistema.
        return enviarParaServidorAutenticacao(mensagem);
    }

    private String enviarParaServidorAutenticacao(String mensagem) {
        // Aqui, você implementará a lógica para se comunicar com o servidor de autenticação
        // Pode ser via sockets, RMI, ou qualquer outro método de comunicação definido
        // Este é apenas um esboço básico. Você precisa implementar a lógica completa aqui.
        // Vou fornecer um exemplo simulado simples:
        return "Resposta do Servidor de Autenticação: Usuário autenticado com sucesso";
    }
}