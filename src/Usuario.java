import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Usuario extends JFrame{
    private static DataOutputStream out;
    private static DataInputStream in;
    private static JTextArea zonaChat;
    private JTextField zonaTexto;
    static String nombre;
    static String nombreFinal;

    public Usuario(String name) {
        setTitle("WhatsApp de "+ name);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        zonaChat = new JTextArea();
        zonaChat.setEditable(false);
        panel.add(new JScrollPane(zonaChat), BorderLayout.CENTER);

        JPanel panelChat = new JPanel(new BorderLayout());
        zonaTexto = new JTextField();
        panelChat.add(zonaTexto, BorderLayout.CENTER);

        JButton boton = new JButton("Enviar");
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String mensaje = zonaTexto.getText();
                    out.writeUTF(name + ": " + mensaje);
                    zonaTexto.setText("");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        panelChat.add(boton, BorderLayout.EAST);

        panel.add(panelChat, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        nombre = JOptionPane.showInputDialog("Escribe tu nombre");
        out.writeUTF(nombre);
        nombreFinal = in.readUTF();
        new Usuario(nombreFinal);

        new Thread(() -> {
            try {
                String respuesta;
                while (true) {
                    respuesta = in.readUTF();
                    zonaChat.append(respuesta + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}