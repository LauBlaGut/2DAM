package com.safa.numeroaleatorio;

import com.safa.numeroaleatorio.ServidorJuego;
import javafx.application.Platform;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloJugador implements Runnable {
    Socket socket;

    public HiloJugador(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String infoCliente = socket.getInetAddress() + ":" + socket.getPort();

        // Log de conexión (Usamos Platform.runLater porque tocamos JavaFX)
        Platform.runLater(() ->
                ServidorJuego.logArea.appendText("Cliente conectado: " + infoCliente + "\n")
        );

        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            boolean conectado = true;

            while (conectado) {
                // Leemos lo que manda el cliente
                String mensaje = dis.readUTF();

                if (mensaje.equals("*")) {
                    conectado = false; // Salir del bucle
                } else {
                    // Logica del juego
                    String respuesta = "";
                    try {
                        int numeroJugador = Integer.parseInt(mensaje);

                        if (numeroJugador == ServidorJuego.numeroSecreto) {
                            respuesta = "¡CORRECTO! Has adivinado el número.";
                        } else {
                            respuesta = "Fallaste. Inténtalo de nuevo.";
                        }
                    } catch (NumberFormatException e) {
                        // El enunciado dice: "si no es un número, no de error"
                        respuesta = "Error: Debes introducir un número entero.";
                    }

                    // Enviamos respuesta
                    dos.writeUTF(respuesta);
                    dos.flush();
                }
            }

        } catch (IOException e) {
            // Error de conexión
        } finally {
            // Log de desconexión
            Platform.runLater(() ->
                    ServidorJuego.logArea.appendText("Cliente desconectado: " + infoCliente + "\n")
            );
            try {
                socket.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}