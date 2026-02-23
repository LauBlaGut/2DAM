import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

import javax.swing.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnrecoverableKeyException,
            KeyStoreException, InvalidKeyException, InvalidKeySpecException {
        Scanner sc = new Scanner(System.in);
        FTPClient ftpClient = new FTPClient();
        String servidor = "localhost";
        int usuariosPendientes = 0;
        int usuariosSolicitudes = 0;

        while (true) {
            System.out.print("Introduce el nombre de usuario (* para salir): ");
            String username = sc.nextLine();

            if (username.equals("*")) {
                String servidorSMTP = "smtp.gmail.com";
                String usuarioSMTP = "lblancogutierrez@safareyes.es";
                String claveSMTP = "ziou xkda cadd ppof";
                String remitente = "lblancogutierrez@safareyes.es";
                int puerto = 587;

                AuthenticatingSMTPClient clienteSMTP = new AuthenticatingSMTPClient();

                try {
                    clienteSMTP.connect(servidorSMTP, puerto);
                    if (!SMTPReply.isPositiveCompletion(clienteSMTP.getReplyCode())) {
                        JOptionPane.showMessageDialog(null, "Error al conectar con el servidor SMTP.");
                    }  else {
                        String asunto = "Resumen de Auditoría FTP - " + LocalDate.now();
                        String cuerpo = "Se han procesado " + usuariosPendientes + " informes correctamente y se han generado " +
                                usuariosSolicitudes + " solicitudes de archivos nuevas.";

                        clienteSMTP.setSender(usuarioSMTP);
                        clienteSMTP.addRecipient(remitente);

                        Writer writer = clienteSMTP.sendMessageData();
                        if (writer != null) {
                            writer.write("Asunto: " + asunto + "\r\n");
                            writer.write("Destinatario: " + remitente + "\r\n");
                            writer.write("\r\n");
                            writer.write(cuerpo);
                            writer.close();
                            clienteSMTP.completePendingCommand();
                        }

                        JOptionPane.showMessageDialog(null, "Correo enviado correctamente.");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al enviar el correo: " + e.getMessage());
                }

                break;
            }

            System.out.print("Introduce la clave: ");
            String clave = sc.nextLine();

            try {
                ftpClient.connect(servidor);
                if (ftpClient.login(username, clave)) {
                    System.out.println("Se ha conectado correctamente con el servidor FTP.");

                    if (ftpClient.changeWorkingDirectory("/REPORTES")) {
                        System.out.println("Directorio cambiado a /REPORTES.");

                        String archivoPendiente = "tarea_pendiente.txt";
                        String archivoLeido = "leido_lbg.txt";
                        String archivoSolicitud = "solicitud.txt";


                        FTPFile[] archivos = ftpClient.listFiles();
                        boolean archivoEncontrado = false;

                        for (FTPFile archivo : archivos) {
                            if (archivo.getName().equals(archivoPendiente)) {
                                archivoEncontrado = true;

                                if (ftpClient.rename(archivoPendiente, archivoLeido)) {
                                    System.out.println("Archivo renombrado a " + archivoLeido);

                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    if (ftpClient.retrieveFile(archivoLeido, outputStream)) {
                                        System.out.println("Contenido del archivo:");
                                        System.out.println(outputStream.toString());
                                    }
                                    outputStream.close();
                                }
                                usuariosPendientes++;
                                break;
                            }
                        }
                        if (!archivoEncontrado) {
                            String mensaje = "Por favor, suba su informe semanal.";
                            InputStream inputStream = new ByteArrayInputStream(mensaje.getBytes());
                            if (ftpClient.storeFile(archivoSolicitud, inputStream)) {
                                System.out.println("Archivo " + archivoSolicitud + " creado y subido.");
                            }
                            inputStream.close();
                            usuariosSolicitudes++;
                        }
                    } else {
                        System.out.println("No se pudo cambiar a la carpeta /REPORTES.");
                    }

                    ftpClient.logout();
                } else {
                    System.out.println("Datos incorrectos.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        System.out.println("Usuarios con archivos pendientes: " + usuariosPendientes);
        System.out.println("Usuarios con solicitudes nuevas: " + usuariosSolicitudes);
        sc.close();

        
    }
}


