import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Hilo extends Thread{
    Socket cSocket;
    ArrayList<DataOutputStream> clientes;
    ArrayList<String> mensajes;
    ArrayList<String> nombres;

    public Hilo(Socket cSocket, ArrayList<DataOutputStream> clientes, ArrayList<String> mensajes, ArrayList<String> nombres) {
        this.cSocket = cSocket;
        this.clientes = clientes;
        this.mensajes = mensajes;
        this.nombres = nombres;
    }

    public void run(){
        try {
            DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
            clientes.add(out);
            DataInputStream dataInputStream = new DataInputStream(cSocket.getInputStream());
            String nombre = dataInputStream.readUTF();

            for (String name : nombres) {
                if (name.equals(nombre)) {
                    nombre = nombre + "_1";
                }
            }
            nombres.add(nombre);
            out.writeUTF(nombre);

            DataInputStream in = new DataInputStream(cSocket.getInputStream());
            String mensaje;
            if (mensajes != null) {
                for (String nMensaje : mensajes) {
                    out.writeUTF(nMensaje);
                }
            }
            while (true) {
                mensaje = in.readUTF();
                mensajes.add(mensaje);
                for (DataOutputStream client : clientes) {
                    try {
                        client.writeUTF(mensaje);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
