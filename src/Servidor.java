import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class Servidor {
    static ArrayList<DataOutputStream> clientes = new ArrayList<>();
    static ArrayList<String> mensajes = new ArrayList<>();
    public static ArrayList<String> nombres = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5000);
        while (true) {
            Socket cSocket = serverSocket.accept();

            Hilo hilo = new Hilo(cSocket, clientes, mensajes, nombres);
            hilo.start();
        }
    }
}