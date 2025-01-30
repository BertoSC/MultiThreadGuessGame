package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GuessServer {
    private final static int PORT = 57780;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket= new ServerSocket(PORT);
        while (true){
            try{
                Socket socket = serverSocket.accept();
                Thread th = new Thread(new GuessServerWorker(socket));
                th.start();
            } catch (IOException ex){

            }
        }

    }
}
