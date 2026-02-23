/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.net.smtp.*;

//ClienteSMTPBasico
public class Actividad1 extends JFrame {
    private static final long serialVersionUID = 1L;

    // Etiquetas y campos para la conexión
    JLabel lab1 = new JLabel("Servidor SMTP:");
    JTextField txtServidor = new JTextField("smtp.gmail.com");
    JLabel lab2 = new JLabel("Puerto:");
    JTextField txtPuerto = new JTextField("587");
    JLabel lab3 = new JLabel("Usuario:");
    JTextField txtUsuario = new JTextField("");
    JLabel lab4 = new JLabel("Contraseña:");
    JPasswordField txtClave = new JPasswordField("");

    // Etiquetas y campos para el envío
    JLabel lab5 = new JLabel("Remitente:");
    JTextField txtRemitente = new JTextField("");
    JLabel lab6 = new JLabel("Destinatario:");
    JTextField txtDestinatario = new JTextField("");
    JLabel lab7 = new JLabel("Asunto:");
    JTextField txtAsunto = new JTextField("");
    JLabel lab8 = new JLabel("Mensaje:");
    JTextArea txtCuerpo = new JTextArea();

    // Botones
    JButton btnConectar = new JButton("Conectar");
    JButton btnEnviar = new JButton("Enviar mensaje");

    // Cliente SMTP
    AuthenticatingSMTPClient cliente = new AuthenticatingSMTPClient();

    private final Container c = getContentPane();

    public Actividad1() {
        super("CLIENTE BÁSICO SMTP - SSL/TLS");
        c.setLayout(null);

        // Posicionamiento de componentes
        // Sección Conexión
        lab1.setBounds(10, 10, 100, 20); txtServidor.setBounds(110, 10, 150, 20);
        lab2.setBounds(270, 10, 50, 20);  txtPuerto.setBounds(320, 10, 50, 20);
        lab3.setBounds(10, 40, 100, 20); txtUsuario.setBounds(110, 40, 150, 20);
        lab4.setBounds(270, 40, 50, 20);  txtClave.setBounds(320, 40, 100, 20);

        btnConectar.setBounds(10, 70, 410, 30);

        // Sección Mensaje
        lab5.setBounds(10, 110, 100, 20); txtRemitente.setBounds(110, 110, 310, 20);
        lab6.setBounds(10, 140, 100, 20); txtDestinatario.setBounds(110, 140, 310, 20);
        lab7.setBounds(10, 170, 100, 20); txtAsunto.setBounds(110, 170, 310, 20);
        lab8.setBounds(10, 200, 100, 20);

        JScrollPane scroll = new JScrollPane(txtCuerpo);
        scroll.setBounds(10, 220, 410, 100);

        btnEnviar.setBounds(10, 330, 410, 30);
        btnEnviar.setEnabled(false);

        // Añadir al contenedor
        c.add(lab1); c.add(txtServidor); c.add(lab2); c.add(txtPuerto);
        c.add(lab3); c.add(txtUsuario); c.add(lab4); c.add(txtClave);
        c.add(btnConectar);
        c.add(lab5); c.add(txtRemitente); c.add(lab6); c.add(txtDestinatario);
        c.add(lab7); c.add(txtAsunto); c.add(scroll);
        c.add(btnEnviar);

        // Botón Conectar / Desconectar
        btnConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (btnConectar.getText().equals("Conectar")) {
                    realizarConexion();
                } else {
                    realizarDesconexion();
                }
            }
        });

        // Botón Enviar
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarCorreo();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 420);
        setVisible(true);
    }

    private void realizarConexion() {
        try {
            String servidor = txtServidor.getText();
            int puerto = Integer.parseInt(txtPuerto.getText());

            // Configurar TLS
            cliente = new AuthenticatingSMTPClient("TLS");
            cliente.connect(servidor, puerto);

            if (SMTPReply.isPositiveCompletion(cliente.getReplyCode())) {
                cliente.ehlo(servidor);

                // Negociación TLS
                if (cliente.execTLS()) {
                    String user = txtUsuario.getText();
                    String pass = new String(txtClave.getPassword());

                    if (cliente.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, user, pass)) {
                        JOptionPane.showMessageDialog(null, "Conexión realizada y Usuario autenticado.");
                        btnConectar.setText("Desconectar");
                        btnEnviar.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error en la autenticación.");
                        cliente.disconnect();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo negociar TLS");
                }
            } else {
                cliente.disconnect();
                JOptionPane.showMessageDialog(null, "Conexión rechazada por el servidor");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se puede realizar la conexión: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void realizarDesconexion() {
        try {
            cliente.disconnect();
            btnConectar.setText("Conectar");
            btnEnviar.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Desconectado del servidor");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void enviarCorreo() {
        try {
            SimpleSMTPHeader header = new SimpleSMTPHeader(
                    txtRemitente.getText(),
                    txtDestinatario.getText(),
                    txtAsunto.getText()
            );

            cliente.setSender(txtRemitente.getText());
            cliente.addRecipient(txtDestinatario.getText());

            Writer writer = cliente.sendMessageData();
            if (writer != null) {
                writer.write(header.toString());
                writer.write(txtCuerpo.getText());
                writer.close();

                if (cliente.completePendingCommand()) {
                    JOptionPane.showMessageDialog(null, "Mensaje enviado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al finalizar el envío");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al enviar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Actividad1();
    }
}

*/
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import org.apache.commons.net.smtp.*;

public class Actividad1 {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnrecoverableKeyException,
            KeyStoreException, InvalidKeyException, InvalidKeySpecException {

        Scanner sc = new Scanner(System.in);

        // Se crea cliente SMTP seguro tal como hace el profesor
        AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();

        // Datos del servidor y credenciales
        String server = "smtp.gmail.com";
        String username = "lblancogutierrez@safareyes.es";
        String password = "ziou xkda cadd ppof"; // Tu contraseña de aplicación
        int puerto = 587;
        String remitente = "lblancogutierrez@safareyes.es";

        try {
            int respuesta;

            // 1. Creación de la clave para canal seguro (paso obligatorio del profesor)
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(null, null);
            KeyManager km = kmf.getKeyManagers()[0];

            // 2. Conexión al servidor
            client.connect(server, puerto);
            System.out.println("1 - " + client.getReplyString());

            // Se establece la clave para la comunicación segura
            client.setKeyManager(km);

            respuesta = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(respuesta)) {
                client.disconnect();
                System.err.println("CONEXIÓN RECHAZADA.");
                System.exit(1);
            }

            // 3. Envío del comando EHLO
            client.ehlo(server);
            System.out.println("2 - " + client.getReplyString());

            // 4. Negociación TLS (Modo no implícito)
            if (client.execTLS()) {
                System.out.println("3 - " + client.getReplyString());

                // 5. Autenticación con el servidor
                if (client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, username, password)) {
                    System.out.println("4 - " + client.getReplyString());

                    // Entrada de datos por consola para personalizar el envío
                    System.out.print("Introduce el correo destino: ");
                    String destino = sc.nextLine();
                    System.out.print("Introduce el asunto: ");
                    String asunto = sc.nextLine();
                    System.out.print("Introduce el cuerpo del mensaje: ");
                    String mensaje = sc.nextLine();

                    // Se crea la cabecera
                    SimpleSMTPHeader cabecera = new SimpleSMTPHeader(remitente, destino, asunto);

                    client.setSender(remitente);
                    client.addRecipient(destino);
                    System.out.println("5 - " + client.getReplyString());

                    // 6. Envío de DATA
                    Writer writer = client.sendMessageData();
                    if (writer == null) {
                        System.out.println("FALLO AL ENVIAR DATA.");
                        System.exit(1);
                    }

                    writer.write(cabecera.toString());
                    writer.write(mensaje);
                    writer.close();
                    System.out.println("6 - " + client.getReplyString());

                    boolean exito = client.completePendingCommand();
                    System.out.println("7 - " + client.getReplyString());

                    if (!exito) {
                        System.out.println("FALLO AL FINALIZAR TRANSACCIÓN.");
                        System.exit(1);
                    } else {
                        System.out.println("MENSAJE ENVIADO CON ÉXITO......");
                    }

                } else {
                    System.out.println("USUARIO NO AUTENTICADO.");
                }
            } else {
                System.out.println("FALLO AL EJECUTAR STARTTLS.");
            }

        } catch (IOException e) {
            System.err.println("Could not connect to server.");
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                client.disconnect();
                sc.close();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }

        System.out.println("Fin del envío.");
        System.exit(0);
    }
}
