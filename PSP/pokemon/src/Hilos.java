import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Hilos implements Runnable {
    Socket socket;

    public Hilos(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // Lógica para manejar la conexión del socket
        try {
            ObjectOutputStream flujoSalidaObjeto = new ObjectOutputStream(socket.getOutputStream());
            flujoSalidaObjeto.flush();

            DataInputStream flujoEntradaDatos = new DataInputStream(socket.getInputStream());

            while (true) {
                String entrada = flujoEntradaDatos.readUTF();
                if (entrada.equals("*")) {
                    socket.close();
                    break;
                }
                int numeroSolicitado = 0;
                try {
                    numeroSolicitado = Integer.parseInt(entrada);
                    System.out.println("El cliente busca el Pokemon #" + numeroSolicitado);
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida recibida");
                }

                // Lógica de búsqueda por NUMERO, no por nombre [cite: 41]
                Pokemon pokemonEncontrado = null;
                for (Pokemon pokemon : Pokedex.listaPokemon) {
                    if (pokemon.getNumeroPokedex() == numeroSolicitado) {
                        pokemonEncontrado = pokemon;
                        break;
                    }
                }

                // Si no existe, crear objeto dummy [cite: 42]
                if (pokemonEncontrado == null) {
                    pokemonEncontrado = new Pokemon(0, "No existe",
                            new TipoElemental("N/A", "N/A"), new Ataque("N/A", 0));
                }

                // Enviar objeto
                flujoSalidaObjeto.writeObject(pokemonEncontrado);
                flujoSalidaObjeto.reset(); // Importante para limpiar caché de objetos
                flujoSalidaObjeto.flush();
            }
        } catch (Exception e) {
            System.out.println("Conexión terminada o error: " + e.getMessage());
        }finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }

}
