import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Entrenador {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 5050;
        Socket clientSocket = new Socket(host, port);

        InputStream inputStream = clientSocket.getInputStream();

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int idCliente = dataInputStream.readInt();
        System.out.println("El id del entrenador es: " + idCliente);

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Introduce el número Pokedex a consultar (o '*' para terminar): ");
            String entrada = scanner.nextLine();
            dataOutputStream.writeUTF(entrada);
            dataOutputStream.flush();

            if (entrada.equals("*")) {
                clientSocket.close();
                break;
            }
            try {
                Pokemon pokemon = (Pokemon) objectInputStream.readObject();

                System.out.println("------------------------------------------------");
                if (pokemon.getNumeroPokedex() == 0) {
                    System.out.println("MENSAJE: El Pokemon no existe en la Pokedex.");
                } else {
                    System.out.println("Datos recibidos:");
                    System.out.println(" - Número: " + pokemon.getNumeroPokedex());
                    System.out.println(" - Nombre: " + pokemon.getNombre());
                    // Usamos los toString() que definimos en las clases o accedemos a sus atributos
                    System.out.println(" - Elemento: " + pokemon.getElemento().toString());
                    System.out.println(" - Ataque: " + pokemon.getAtq().toString());
                }
                System.out.println("------------------------------------------------");

            } catch (ClassNotFoundException e) {
                System.out.println("Error: Clase Pokemon no encontrada.");
            }
        }


    }
}
