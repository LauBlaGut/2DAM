package com.safa.numeroaleatorio;

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

public class ClienteJuego extends Application {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label lblInstruccion = new Label("Introduce un número (0-25):");
        TextField txtNumero = new TextField();

        Button btnEnviar = new Button("Enviar");
        Button btnLimpiar = new Button("Limpiar");
        Button btnSalir = new Button("Salir");

        Label lblResultado = new Label("Esperando respuesta...");
        lblResultado.setStyle("-fx-font-weight: bold");

        root.getChildren().addAll(lblInstruccion, txtNumero, btnEnviar, btnLimpiar, btnSalir, lblResultado);

        // --- Conexión al iniciar ---
        try {
            socket = new Socket("localhost", 5000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            lblResultado.setText("Error: No se puede conectar al servidor.");
            btnEnviar.setDisable(true);
        }

        // --- Botón ENVIAR ---
        btnEnviar.setOnAction(e -> {
            try {
                String numero = txtNumero.getText();
                dos.writeUTF(numero); // Enviamos el número (o texto)
                dos.flush();

                // Esperamos respuesta del servidor
                String respuesta = dis.readUTF();
                lblResultado.setText(respuesta);
            } catch (IOException ex) {
                lblResultado.setText("Error de comunicación.");
            }
        });

        // --- Botón LIMPIAR ---
        btnLimpiar.setOnAction(e -> {
            txtNumero.clear();
            lblResultado.setText("Esperando respuesta...");
        });

        // --- Botón SALIR ---
        btnSalir.setOnAction(e -> cerrarYSalir());
        stage.setOnCloseRequest(e -> cerrarYSalir());

        Scene scene = new Scene(root, 300, 350);
        stage.setTitle("Cliente Juego");
        stage.setScene(scene);
        stage.show();
    }

    private void cerrarYSalir() {
        try {
            if (dos != null) {
                dos.writeUTF("*"); // Enviamos asterisco para avisar desconexión
                dos.flush();
            }
            if (socket != null) socket.close();
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