package com.safa.numeroaleatorio;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServidorJuego extends Application {

    // Hacemos esto static para que los hilos puedan acceder fácilmente
    public static int numeroSecreto = 0;
    public static TextArea logArea; // El visor de logs

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label lblTitulo = new Label("Servidor: Adivina el Número");
        logArea = new TextArea();
        logArea.setEditable(false); // Que no se pueda escribir a mano
        
        Button btnGenerar = new Button("Generar Nuevo Número (0-25)");
        Label lblSecreto = new Label("Número actual: " + numeroSecreto);

        // Acción del botón Generar
        btnGenerar.setOnAction(e -> {
            Random rand = new Random();
            numeroSecreto = rand.nextInt(26); // 0 a 25
            lblSecreto.setText("Número actual: " + numeroSecreto);
            logArea.appendText(">> SE HA GENERADO UN NUEVO NÚMERO SECRETO\n");
        });

        root.getChildren().addAll(lblTitulo, btnGenerar, lblSecreto, logArea);

        // --- Hilo para aceptar conexiones sin congelar la ventana ---
        Thread hiloServer = new Thread(() -> {
            try {
                int puerto = 5000;
                ServerSocket servidor = new ServerSocket(puerto);
                
                // Usamos Platform.runLater para tocar la GUI desde otro hilo
                Platform.runLater(() -> logArea.appendText("Servidor iniciado en puerto " + puerto + "\n"));

                while (true) {
                    Socket socket = servidor.accept();
                    
                    // Creamos el hilo trabajador
                    HiloJugador hilo = new HiloJugador(socket);
                    new Thread(hilo).start();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        hiloServer.setDaemon(true); // Para que se cierre si cerramos la ventana
        hiloServer.start();

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Servidor Juego");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}