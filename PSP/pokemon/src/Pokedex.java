import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Pokedex {
    static ArrayList<Pokemon> listaPokemon = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int puerto = 5050;
        int idCliente = 1;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado en el puerto " + puerto);

        //Inicializamos los datos
        listaPokemon.add(new Pokemon(2, "Ivysaur", new TipoElemental("Planta", "Fuego"), new Ataque("Latigo Cepa", 40)));
        listaPokemon.add(new Pokemon(4, "Charmander", new TipoElemental("Fuego", "Agua"), new Ataque("Ascuas", 40)));
        listaPokemon.add(new Pokemon(7, "Squirtle", new TipoElemental("Agua", "Planta"), new Ataque("Pistola Agua", 40)));

        while (true){
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado");

            // 1. Enviar ID al cliente (DataOutputStream)
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(idCliente);
            dos.flush();

            // 2. Crear hilo
            Hilos hilo = new Hilos(socket);
            Thread thread = new Thread(hilo);
            thread.start();

            idCliente++;
        }




    }
}
