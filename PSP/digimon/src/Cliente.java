import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args)throws IOException {
        String host = "localhost";
        int port = 5000; //mismo puerto que el del servidor
        Socket cliente = new Socket(host, port);

        //llamada al servidor, leemos el id que nos está enviando el servidor
        // tiramos tubería para meter información
        InputStream inputStream = cliente.getInputStream();

        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int idCliente = dataInputStream.readInt();
        System.out.println("El id del cliente es: " + idCliente);
        //
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        // tiramos tubería para sacar información

        DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());
        //
        while(true) {
            // Pedimos al usuario que introduzca el nombre del personaje a consultar
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce el nombre del personaje a consultar (o '*' para terminar): ");
            String nombrePersonaje = scanner.nextLine(); //leemos la línea completa, input que espera un string
            // Enviamos el nombre del personaje al servidor
            dataOutputStream.writeUTF(nombrePersonaje);

            //para salir
            if (nombrePersonaje.equals("*")) {
                cliente.close();
                break;
            }

            try{
                PersonajeOnePiece pop = (PersonajeOnePiece) objectInputStream.readObject();
                System.out.println("El nombre del personaje es: " + pop.getNombre()+"\nEl rol del personaje es: " + pop.getRol()+"\nLa fruta del personaje es: "+pop.frutaDiablo+"\nEl barco del personaje es: "+pop.nombreBarco);
                System.out.println("¿Quiéres saber los datos del barco? (S/N)");
                String nombreBarco = scanner.nextLine();
                boolean hecho2 = false;
                do {
                    if (nombreBarco.equals("S")) {
                        hecho2 = true;
                        System.out.println("El nombre del barco es: " + pop.nombreBarco.nombre + "\nLos tripulantes son: " + pop.nombreBarco.tripulantes + "\nEl tipo es: " + pop.nombreBarco.tipo + "\nLa altura es: " + pop.nombreBarco.altura + "\nLa longitud es: " + pop.nombreBarco.longitud);
                    }
                    if (nombreBarco.equals("N")) {
                        hecho2 = true;
                    } else {
                        System.out.println("Escriba S/N");
                    }
                }while(!hecho2);
                System.out.println("¿Quieres saber los datos de la fruta? (S/N)");
                String frutaDiablo = scanner.nextLine();
                boolean hecho = false;
                do {
                    if (frutaDiablo.equals("S")) {
                        System.out.println("El nombre de la fruta es: " + pop.frutaDiablo.nombre + "\nLa descripción es: " + pop.frutaDiablo.descripcion + "\nLa apariencia es: " + pop.frutaDiablo.apariencia);
                        hecho = true;
                    } else if (frutaDiablo.equals("N")) {
                        hecho = true;
                    } else {
                        System.out.println("Escriba S/N");
                    }
                } while (!hecho);

            }catch(ClassNotFoundException e){
                System.out.println("No se encontró el objeto.");
            }

        }



    }
}
