package LojaDeCarros;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4096);
            System.out.println("Conectado ao servidor gateway.");

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

            outToServer.println("Mensagem do cliente para o servidor gateway");

            String respostaDoServidor = inFromServer.readLine();
            System.out.println("Resposta do servidor gateway: " + respostaDoServidor);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
