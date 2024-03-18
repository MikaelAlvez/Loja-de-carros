package LojaDeCarros;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class ServidorGateway {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4096, 0, InetAddress.getLocalHost())) {
            System.out.println("Servidor Gateway iniciado." +
                    " [Porta: " + serverSocket.getLocalPort() + ", Endereço IP: " + serverSocket.getInetAddress().getHostAddress() + ", HostName: " + serverSocket.getInetAddress().getHostName() + "]\n");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Aguardando conexões...");
                }
            }, 0, 15000);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                String message = inFromClient.readLine();
                System.out.println(message);
                
                System.out.println("Cliente conectado: " + clientSocket);

                Thread clientThread = new Thread(new ClienteHandler(clientSocket));
                clientThread.start();

                try {
                    Socket autenticacaoSocket = new Socket("localhost", 4097);
                    PrintWriter outToAutenticacao = new PrintWriter(autenticacaoSocket.getOutputStream(), true);
                    BufferedReader inFromAutenticacao = new BufferedReader(new InputStreamReader(autenticacaoSocket.getInputStream()));

                    // Enviar solicitação de autenticação
                    outToAutenticacao.println("Autenticar");

                    // Receber resposta de autenticação
                    String respostaAutenticacao = inFromAutenticacao.readLine();
                    System.out.println("Resposta do servidor de autenticação: " + respostaAutenticacao);

                    // Fechar a conexão com o ServidorAutenticacao
                    autenticacaoSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
