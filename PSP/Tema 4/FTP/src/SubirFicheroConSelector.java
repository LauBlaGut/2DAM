import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SubirFicheroConSelector {
    public static void main(String[] args) {
        FTPClient cliente = new FTPClient();

        String servidor = "localhost";
        String user = "usuario1";
        String pasw = "usuario1";

        try {
            // 1. SELECCIONAR FICHERO DEL DISCO DURO
            System.out.println("Selecciona un fichero para subir...");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Elige el fichero a subir al FTP");

            int seleccion = fileChooser.showOpenDialog(null);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File ficheroSeleccionado = fileChooser.getSelectedFile();
                String rutaFichero = ficheroSeleccionado.getAbsolutePath();
                String nombreFichero = ficheroSeleccionado.getName();

                System.out.println("Fichero seleccionado: " + nombreFichero);

                // 2. CONEXIÓN AL FTP
                System.out.println("Conectandose a " + servidor + "...");
                cliente.connect(servidor);
                boolean login = cliente.login(user, pasw);

                if (login) {
                    System.out.println("Login correcto.");

                    // Configuración básica importante
                    cliente.enterLocalPassiveMode(); // Evita problemas de firewall
                    cliente.setFileType(FTP.BINARY_FILE_TYPE); // Para subir imágenes, pdfs, zips, etc. sin corromperlos

                    // 3. SUBIR EL FICHERO
                    // Creamos el flujo de entrada desde el fichero de nuestro disco
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(rutaFichero));

                    System.out.println("Subiendo fichero \"" + nombreFichero + "\" al directorio raíz...");

                    // storeFile(nombre_remoto, flujo_entrada)
                    if (cliente.storeFile(nombreFichero, in)) {
                        System.out.println(">>> ¡Fichero subido con éxito! <<<");
                    } else {
                        System.out.println(">>> Error: No se ha podido subir el fichero.");
                    }

                    in.close(); // Cerramos el flujo del fichero local

                    // 4. LISTAR DIRECTORIO RAÍZ PARA COMPROBAR
                    System.out.println("\n--- Contenido del Directorio Raíz del Servidor ---");
                    FTPFile[] archivos = cliente.listFiles();

                    boolean encontrado = false;
                    for (FTPFile archivo : archivos) {
                        System.out.println("- " + archivo.getName());
                        if (archivo.getName().equals(nombreFichero)) {
                            encontrado = true;
                        }
                    }

                    if (encontrado) {
                        System.out.println("\nVERIFICACIÓN: El archivo aparece en el listado.");
                    } else {
                        System.out.println("\nVERIFICACIÓN: El archivo NO aparece (algo falló).");
                    }

                    // Desconectar
                    cliente.logout();
                    cliente.disconnect();

                } else {
                    System.out.println("Error de Login: Comprueba usuario y contraseña.");
                }
            } else {
                System.out.println("Operación cancelada por el usuario (no se eligió fichero).");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}