package com.safa.chatudp_2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Optional;

public class ClienteChatUDP extends Application {
    private static final String IP_MULTICAST = "230.0.0.1";
    private static final int PUERTO = 4446;

    private MulticastSocket socket;
    private InetAddress grupo;
    private boolean leyendo = true;
    private String nombreUsuario = "Usuario";

    @Override
    public void start(Stage stage) {
        // 1. Pedir nombre al usuario
        TextInputDialog dialog = new TextInputDialog("Cliente");
        dialog.setTitle("Login");
        dialog.setHeaderText("Configuración de Usuario");
        dialog.setContentText("Introduce tu nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            nombreUsuario = result.get();
        } else {
            Platform.exit();
            return;
        }

        // --- Interfaz Gráfica ---
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label label = new Label("Bienvenido, " + nombreUsuario);

        // TextArea para visualizar mensajes del servidor
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(300);

        Button btnSalir = new Button("Salir"); // [cite: 10]
        root.getChildren().addAll(label, textArea, btnSalir);

        // --- Lógica de Red (Hilo separado) ---
        Thread hiloEscucha = new Thread(() -> {
            try {
                // El cliente se ata al puerto para ESCUCHAR
                socket = new MulticastSocket(PUERTO);
                grupo = InetAddress.getByName(IP_MULTICAST);

                // IMPORTANTE: Unirse al grupo
                socket.joinGroup(grupo);

                Platform.runLater(() -> textArea.appendText("--- Conectado al grupo Multicast ---\n"));

                while (leyendo) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

                    // Se queda bloqueado aquí esperando mensajes del servidor
                    socket.receive(paquete);

                    String msg = new String(paquete.getData(), 0, paquete.getLength());

                    // Actualizar UI
                    Platform.runLater(() -> textArea.appendText("SERVIDOR DICE: " + msg + "\n"));
                }
            } catch (IOException e) {
                if (leyendo) Platform.runLater(() -> textArea.appendText("Error: " + e.getMessage()));
            }
        });
        hiloEscucha.setDaemon(true);
        hiloEscucha.start();

        // --- Eventos ---
        btnSalir.setOnAction(e -> salir());
        stage.setOnCloseRequest(e -> salir());

        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("Cliente: " + nombreUsuario);
        stage.setScene(scene);
        stage.show();
    }

    private void salir() {
        leyendo = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.leaveGroup(grupo); // Salir del grupo antes de cerrar
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
