package com.safa.chatudp_2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServidorChatUDP extends Application {
    // Configuración de red
    private static final String IP_MULTICAST = "230.0.0.1";
    private static final int PUERTO = 4446;

    private MulticastSocket socket;
    private InetAddress grupo;

    @Override
    public void start(Stage stage) {
        // --- Interfaz Gráfica (Requisito: Campo texto, TextArea, Botones) ---
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label label = new Label("Servidor Multicast (Emisor)");

        // Área donde se muestran los mensajes enviados
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefHeight(300);

        // Campo para escribir el mensaje
        TextField textField = new TextField();
        textField.setPromptText("Escribe el mensaje aquí...");

        Button btnEnviar = new Button("Enviar");
        Button btnSalir = new Button("Salir"); //

        HBox botones = new HBox(10, btnEnviar, btnSalir);
        root.getChildren().addAll(label, textArea, textField, botones);

        // --- Lógica de Red ---
        try {
            socket = new MulticastSocket();
            grupo = InetAddress.getByName(IP_MULTICAST);
        } catch (IOException e) {
            textArea.setText("Error iniciando socket: " + e.getMessage());
        }

        // --- Eventos ---

        // Botón Enviar: Envía mensaje al grupo multicast
        btnEnviar.setOnAction(e -> {
            String mensaje = textField.getText();
            if (!mensaje.isEmpty()) {
                enviarMensaje(mensaje);
                textArea.appendText("Enviado: " + mensaje + "\n");
                textField.clear();
            }
        });

        // Botón Salir: Finaliza la ejecución
        btnSalir.setOnAction(e -> cerrarAplicacion());
        stage.setOnCloseRequest(e -> cerrarAplicacion());

        Scene scene = new Scene(root, 400, 450);
        stage.setTitle("Servidor Chat UDP");
        stage.setScene(scene);
        stage.show();
    }

    private void enviarMensaje(String mensaje) {
        try {
            byte[] buffer = mensaje.getBytes();
            // Creamos el datagrama dirigido al GRUPO y al PUERTO
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, PUERTO);
            socket.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cerrarAplicacion() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}