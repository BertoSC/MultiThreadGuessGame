package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class GuessServerWorker implements Runnable{
    private Socket socket;
    private int numSecreto;
    private int intentos;
    private boolean jugando=false;
    private BufferedReader in;
    private PrintWriter out;

    public GuessServerWorker(Socket socket) throws IOException {
        this.socket=socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            out.println("10 Number game server ready");
            String comando;

            while ((comando=in.readLine())!=null){
                responderAccion(comando);
            }

        } catch (IOException ex){
            out.println("15 Number game server ready");
        }

    }

    private void responderAccion(String comando) throws IOException {
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
                socket.close();
                break;
            default:
                out.println("90 UNKNOWN");
                break;

        }
    }

    private void mostrarInformacion() {
        out.println("40 INFO"+
                "NEW. This command indicates that the client wants to start a new game. As an argument it accept the number of tries the user want to have to guess the number. Example: NEW 8"+
                        "HELP. The client asks the server for information about the game and the commands to use."+
                        "QUIT. The client sends the request to terminate the communication with the server.");
    }

    private void empezarPartida(String[] argumentos) {
        if (argumentos.length != 2){
            out.println("80 ERR");
            return;
        }
        try {
            this.intentos = Integer.parseInt(argumentos[1]);
            this.jugando = true;
            Random rand = new Random();
            this.numSecreto = rand.nextInt(100) + 1;
            out.println("20 PLAY <" + intentos + ">");
        }catch (NumberFormatException num){
            out.println("80 ERR");
        }

    }

    private void gestionarAdivinanza(String[] argumentos) {

        if (!jugando){
            out.println("80 ERR");
            return;
        }

        if (intentos==0){
            out.println("70 LOSE NUM <"+numSecreto+">");
            this.jugando=false;
            return;
        }

        if (argumentos.length!=2){
            out.println("90 UNKNOWN");
            return;
        }

        int intento = Integer.parseInt(argumentos[1]);

        if (intento<numSecreto){
            intentos--;
            out.println("25 LOW <"+intentos+">");
            return;
        }

        if (intento>numSecreto){
            intentos--;
            out.println("35 HIGH <"+intentos+">");
            return;
        }

        if (intento==numSecreto){
            out.println("50 WIN");
            this.jugando=false;
            return;
        }



    }


}
