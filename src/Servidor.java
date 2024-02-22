import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
public class Servidor {
    static ArrayList<DataOutputStream> clientes;
    static ArrayList<String> mensajes = new ArrayList<>();
    public static ArrayList<String> nombres = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        clientes = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(5000);
        while (true) {

            Socket cSocket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
            clientes.add(out);
            DataInputStream dataInputStream = new DataInputStream(cSocket.getInputStream());
            String nombre = dataInputStream.readUTF();

            for(String name : nombres){
                if (name.equals(nombre)) {
                    nombre = nombre+"_1";
                }
            }
            nombres.add(nombre);
            out.writeUTF(nombre);

            new Thread(() -> {
                try {
                    DataInputStream in = new DataInputStream(cSocket.getInputStream());
                    String mensaje;
                    if(mensajes!=null){
                        escribirHistorial(mensajes, cSocket);
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
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

    private static void escribirHistorial(ArrayList<String> mensajes, Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        for (String mensaje: mensajes){
            out.writeUTF(mensaje);
        }
    }
}