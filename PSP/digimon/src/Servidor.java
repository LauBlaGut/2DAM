import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    static ArrayList<PersonajeOnePiece> listaPersonajes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int puerto = 5000;
        int idCliente = 1;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado en el puerto " + puerto);

        // Inicializamos los datos
        listaPersonajes.add(new PersonajeOnePiece("Luffy", "Capitán", new Fruta("Gomu Gomu no Mi", "Fruta de la goma", "Apariencia de una ciruela"), new Barco("Thousand Sunny", "Barco de los Sombrero de Paja", "hola", 3, 3)));
        listaPersonajes.add(new PersonajeOnePiece("Zoro", "Espadachín", new Fruta("No tiene", "No tiene", "No tiene"), new Barco("Thousand Sunny", "Barco de los Sombrero de Paja", "adios", 2, 3)));
        listaPersonajes.add(new PersonajeOnePiece("Nami", "Navegante", new Fruta("No tiene", "No tiene", "No tiene"), new Barco("Thousand Sunny", "Barco de los Sombrero de Paja", "hola", 1, 3)));

        while (true) {
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado");

            // 1. Enviar ID al cliente (DataOutputStream)
            // IMPORTANTE: No cierres este stream aquí, o cerrarás el socket.
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(idCliente);
            dos.flush(); // Empujamos el dato para que llegue ya

            // 2. Crear hilo
            Hilos hilo = new Hilos(socket);
            Thread thread = new Thread(hilo);
            thread.start();

            idCliente++;
        }
    }
}

class Hilos implements Runnable {
    Socket socket;

    public Hilos(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run()  {
        try {
            // --- CORRECCIÓN CLAVE ---
            // 1. Creamos los flujos FUERA del while.
            // 2. Creamos PRIMERO el de Salida de Objetos y hacemos FLUSH para enviar la cabecera.
            // Esto desbloquea el 'new ObjectInputStream' del cliente.

            ObjectOutputStream flujoSalidaObjeto = new ObjectOutputStream(socket.getOutputStream());
            flujoSalidaObjeto.flush(); // ¡Fundamental! Envía la cabecera.

            // Ahora creamos el de entrada para leer el nombre (String)
            DataInputStream flujoEntradaDatos = new DataInputStream(socket.getInputStream());

            while (true) {
                // Leemos el nombre
                // Si el cliente se desconecta, readUTF lanzará EOFException o IOException
                String nombrePersonaje = flujoEntradaDatos.readUTF();
                if (nombrePersonaje.equals("*")) {
                    socket.close();
                    break;
                }
                System.out.println("El cliente busca a: " + nombrePersonaje);

                // Lógica de búsqueda
                PersonajeOnePiece personajeEncontrado = null;
                for (PersonajeOnePiece personaje : Servidor.listaPersonajes) {
                    if (personaje.getNombre().equalsIgnoreCase(nombrePersonaje)) {
                        personajeEncontrado = personaje;
                        break;
                    }
                }

                if (personajeEncontrado == null) {
                    System.out.println("No encontrado, enviando placeholder.");
                    personajeEncontrado = new PersonajeOnePiece("No existe", "No existe", new Fruta("No existe", "No existe", "No existe"), new Barco("No existe", "No existe", "No existe", 0, 0));
                } else {
                    System.out.println("Encontrado: " + personajeEncontrado.getNombre());
                }

                // Enviamos el objeto reutilizando el flujo creado fuera del bucle
                flujoSalidaObjeto.writeObject(personajeEncontrado);

                // IMPORTANTE: reset() borra la caché del ObjectOutputStream.
                // Sin esto, si envías el mismo objeto modificado, Java enviará la versión vieja.
                flujoSalidaObjeto.reset();
                flujoSalidaObjeto.flush();
            }

        } catch (EOFException e) {
            System.out.println("El cliente se ha desconectado.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}