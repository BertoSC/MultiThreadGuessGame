package org.example;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GuessServer {
    private final static int PORT = 57780;

    public static void main(String[] args) throws IOException {
        SSLSocket socket = null;
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/ServerKeys.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "12345678");
        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket SSLServer = (SSLServerSocket) sslServerSocketFactory.createServerSocket(PORT);
        //ServerSocket serverSocket= new ServerSocket(PORT);
        while (true){
            try{
                //Socket socket = serverSocket.accept();
                socket = (SSLSocket) SSLServer.accept();
                Thread th = new Thread(new GuessServerWorker(socket));
                th.start();
            } catch (IOException ex){

            }
        }

    }
}
