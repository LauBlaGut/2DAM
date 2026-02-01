package com.safa.modelotcp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteMayusculas extends Application {

    // Configuración de conexión
    private static final String HOST = "localhost";
    private static final int PUERTO = 44445;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    @Override
    public void start(Stage primaryStage) {
        // --- Interfaz Gráfica ---
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label lblInstruccion = new Label("Introduce una cadena:");
        TextField txtEntrada = new TextField(); // [cite: 17]

        Label lblResultado = new Label("Resultado:");
        Label lblSalida = new Label("---"); // Aquí se muestra la respuesta [cite: 17]
        lblSalida.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button btnEnviar = new Button("Enviar");
        Button btnLimpiar = new Button("Limpiar"); //
        Button btnSalir = new Button("Salir"); //

        // Organizamos botones
        VBox botones = new VBox(10, btnEnviar, btnLimpiar, btnSalir);
        botones.setAlignment(Pos.CENTER);

        root.getChildren().addAll(lblInstruccion, txtEntrada, btnEnviar, lblResultado, lblSalida, botones);

        // --- Lógica de Conexión ---
        try {
            socket = new Socket(HOST, PUERTO);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            lblSalida.setText("Error conectando al servidor: " + e.getMessage());
            btnEnviar.setDisable(true);
        }

        // --- Eventos de Botones ---

        // 1. Botón ENVIAR [cite: 17]
        btnEnviar.setOnAction(e -> {
            try {
                String texto = txtEntrada.getText();
                if (!texto.isEmpty()) {
                    // Enviar al servidor
                    dos.writeUTF(texto);
                    dos.flush();

                    // Recibir respuesta (bloqueante, pero rápido para este ejemplo)
                    String respuesta = dis.readUTF();

                    // Mostrar debajo
                    lblSalida.setText(respuesta);
                }
            } catch (IOException ex) {
                lblSalida.setText("Error de comunicación.");
            }
        });

        // 2. Botón LIMPIAR
        btnLimpiar.setOnAction(e -> {
            txtEntrada.clear();
            lblSalida.setText("---");
        });

        // 3. Botón SALIR
        btnSalir.setOnAction(e -> cerrarConexion());

        // Manejar cierre de ventana con la X
        primaryStage.setOnCloseRequest(e -> cerrarConexion());

        Scene scene = new Scene(root, 300, 350);
        primaryStage.setTitle("Cliente Mayúsculas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cerrarConexion() {
        try {
            if (dos != null) {
                dos.writeUTF("*"); // Enviamos asterisco para avisar al servidor [cite: 16, 18]
                dos.flush();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Platform.exit();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
