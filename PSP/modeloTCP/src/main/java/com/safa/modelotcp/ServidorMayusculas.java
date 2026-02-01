package com.safa.modelotcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;
import java.net.*;

public class ServidorMayusculas {
    public static void main(String[] args) {
        int puerto = 44445;

        try {
            ServerSocket servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado...");

            // Bucle infinito: Nunca deja de escuchar
            while (true) {
                // 1. El servidor se queda "congelado" aquí esperando a que alguien entre
                Socket cliente = servidor.accept();

                // Si pasa de esta línea, es que alguien se ha conectado
                System.out.println("=>Conecta IP " + cliente.getInetAddress() +
                        ", Puerto remoto: " + cliente.getPort());

                // 2. Preparamos el trabajo para el hilo
                HiloCliente tarea = new HiloCliente(cliente);

                // 3. CLAVE: Creamos el hilo y lo arrancamos (.start)
                // Esto hace que Java ejecute el método run() de la clase HiloCliente
                // EN PARALELO, mientras el main vuelve arriba al 'accept'
                Thread t = new Thread(tarea);
                t.start();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}

class HiloCliente implements Runnable {
    Socket s; // Usamos nombres cortos, tipico de examen

    public HiloCliente(Socket socket) {
        this.s = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            boolean conectado = true;

            while (conectado) {
                // Leer mensaje
                String mensaje = in.readUTF();

                if (mensaje.equals("*")) {
                    conectado = false; // Salimos del bucle
                } else {
                    // Convertir a mayúsculas y devolver
                    String respuesta = mensaje.toUpperCase();
                    out.writeUTF(respuesta);
                    out.flush(); // Importante para que se envíe ya
                }
            }

        } catch (IOException e) {
            // Si el cliente cierra a lo bruto, entra aquí.
            // No hacemos nada o imprimimos un error simple.
        } finally {
            // Esto se ejecuta siempre al desconectarse
            try {
                System.out.println("=>Desconecta IP " + s.getInetAddress() +
                        ", Puerto remoto: " + s.getPort());
                s.close();
            } catch (IOException e) {
                // Da igual
            }
        }
    }
}
