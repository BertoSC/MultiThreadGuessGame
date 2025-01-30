package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GuessServerWorker implements Runnable{
    private Socket socket;
    private int numSecreto;
    private int intentos;
    private boolean jugando=false;
    private BufferedReader in;
    private PrintWriter out;

    public GuessServerWorker(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new PrintWriter(socket.getOutputStream(), true)){

            out.println("10 Number game server ready");
            String comando;

            while ((comando=in.readLine())!=null){
                responderAccion(comando);
            }

        } catch (IOException ex){

        }

    }

    private void responderAccion(String comando) {
        String[] argumentos = comando.split(" ");
        String peticion = argumentos[0];

        switch(peticion){
            case "NEW":
                empezarPartida(argumentos);
                break;
            case "NUM":
                gestionarAdivinanza(argumentos);
                break;
            case "HELP":
                mostrarInformacion();
                break;
            case "QUIT":
                out.println("11 BYE");
                break;
            default:
                out.println("90 UNKNOWN");

        }
    }

    private void mostrarInformacion() {
        out.println("40 INFO");
        out.println("NEW. This command indicates that the client wants to start a new game. As an argument it accept the number of tries the user want to have to guess the number. Example: NEW 8");
        out.println("NUM. The client sends its guess to the server. A new game has to be created before using this command. Example: NUM 42.");
        out.println("HELP. The client asks the server for information about the game and the commands to use.");
        out.println("QUIT. The client sends the request to terminate the communication with the server.");
    }

    private void empezarPartida(String[] argumentos) {
        if (argumentos.length != 2){
            out.println("80 ERR");
            return;
        }
        try {
            intentos = Integer.parseInt(argumentos[1]);
            jugando = true;
            out.println("20 PLAY <" + intentos + ">");
        }catch (NumberFormatException num){
            System.out.println("80 ERR");
        }

    }

    private void gestionarAdivinanza(String[] argumentos) {
    }


}
