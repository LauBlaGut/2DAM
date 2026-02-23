package DIREC;

import org.apache.commons.net.ftp.*;
import org.apache.commons.net.smtp.*;
import java.io.*;
import java.util.Scanner;
import java.util.Date;

public class Actividad2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String usuario = "";
        String clave = "";
        int contadorExitosos = 0; // Para el resumen final [cite: 48]

        FTPClient clienteFTP = new FTPClient();
        String servidor = "localhost"; // Servidor según examen [cite: 27]

        // Proceso repetitivo que finaliza con "*" [cite: 26]
        while (true) {
            System.out.print("Introduce nombre de usuario (o * para terminar): ");
            usuario = sc.nextLine();

            if (usuario.equals("*")) break;

            System.out.print("Introduce contraseña: ");
            clave = sc.nextLine();

            try {
                clienteFTP.connect(servidor);
                boolean login = clienteFTP.login(usuario, clave);

                if (login) {
                    System.out.println("Usuario autenticado correctamente.");
                    contadorExitosos++;

                    // 1. Acceder al directorio /LOG [cite: 29]
                    // IMPORTANTE: En tu PC, renombra "usuario1.LOG" a "usuario1" y crea dentro "LOG"
                    // ... dentro del if (login) ...
                    if (clienteFTP.changeWorkingDirectory("LOG")) {
                        System.out.println("Directorio /LOG localizado.");
                        File archivoLocal = new File("temp_log.txt");

                        // Intentamos descargar el contenido existente
                        FileOutputStream fos = new FileOutputStream(archivoLocal);
                        boolean existeEnServidor = clienteFTP.retrieveFile("LOG.txt", fos);
                        fos.close();

                        // Escribimos (si no existía, se crea nuevo; si existía, se añade al final)
                        PrintWriter pw = new PrintWriter(new FileWriter(archivoLocal, true));
                        if (!existeEnServidor) {
                            pw.println("Archivo de log inicializado.");
                        }
                        pw.println("Hora de conexión: " + new Date().toString());
                        pw.close();

                        // Subimos el archivo actualizado
                        FileInputStream fis = new FileInputStream(archivoLocal);
                        clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);
                        if (clienteFTP.storeFile("LOG.txt", fis)) {
                            System.out.println("¡ÉXITO! Registro guardado en el servidor.");
                        } else {
                            System.out.println("Error: No se pudo subir el archivo. Revisa permisos en FileZilla.");
                        }
                        fis.close();
                        archivoLocal.delete();
                    } else {
                        System.out.println("Error: No se encuentra el directorio /LOG");
                    }
                    clienteFTP.logout();
                } else {
                    System.out.println("Error: Usuario o contraseña incorrectos.");
                }
                clienteFTP.disconnect();

            } catch (IOException e) {
                System.out.println("Error de conexión FTP: " + e.getMessage());
            }
        }

        // Al finalizar, enviar correo SMTP con el total [cite: 48, 49]
        enviarResumenSMTP(contadorExitosos);
    }

    private static void enviarResumenSMTP(int total) {
        // Uso de AuthenticatingSMTPClient con TLS [cite: 49, 10]
        AuthenticatingSMTPClient mailClient = new AuthenticatingSMTPClient("TLS");
        String servidorMail = "smtp.gmail.com";
        String user = "lblancogutierrez@safareyes.es";
        String pass = "ziou xkda cadd ppof"; // Tu contraseña de aplicación

        try {
            mailClient.connect(servidorMail, 587);
            mailClient.ehlo(servidorMail);

            if (mailClient.execTLS()) {
                if (mailClient.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, user, pass)) {

                    // Cabecera solicitada [cite: 49]
                    SimpleSMTPHeader header = new SimpleSMTPHeader(user, user, "Resumen conexiones FTP");
                    mailClient.setSender(user);
                    mailClient.addRecipient(user);

                    Writer writer = mailClient.sendMessageData();
                    if (writer != null) {
                        writer.write(header.toString());
                        // Cuerpo con el número de usuarios correctos [cite: 48]
                        writer.write("Número de usuarios correctos que se han conectado: " + total);
                        writer.close();
                        mailClient.completePendingCommand();
                        System.out.println("Correo enviado con el total: " + total);
                    }
                }
            }
            mailClient.disconnect();
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }
    }
}