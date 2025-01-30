package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GuessClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 57780);
            var flujoIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var flujoOut = new PrintWriter(socket.getOutputStream(), true);
            String inicioServer = flujoIn.readLine();
            System.out.println(inicioServer);
            while (true){
                Scanner entrada = new Scanner(System.in);
                System.out.println("Introduce el comando de petición: ");
                String peticion = entrada.nextLine();
                if (peticion.equalsIgnoreCase("QUIT")){
                    flujoOut.println(peticion);
                    String respuesta = flujoIn.readLine();
                    System.out.println(respuesta);
                    break;
                }
                flujoOut.println(peticion);
                String respuesta = flujoIn.readLine();
                System.out.println(respuesta);
            }

        } catch (IOException e) {
            System.err.println("Error de entrada y salida: "+ e.getMessage());
        }
    }
}
