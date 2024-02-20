import javax.swing.*;
import java.awt.*;

public class Usuario extends JFrame {
    private JTextArea zonaChat;
    private JTextField zonaTexto;
    public Usuario (){
        setTitle("WhatsApp");
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

        panelChat.add(boton, BorderLayout.EAST);

        panel.add(panelChat, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        Usuario u = new Usuario();
    }
}
