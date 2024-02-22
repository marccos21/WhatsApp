import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    public static void main(String[] args) throws IOException {

        ArrayList<DataOutputStream> clientes = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(5000);
        while (true) {
            Socket cSocket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
            clientes.add(out);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        DataInputStream in = new DataInputStream(cSocket.getInputStream());
                        String mensaje;
                        while (true) {

                            if (!((mensaje = in.readUTF()) != null)) break;
                            for (DataOutputStream client : clientes) {
                                client.writeUTF(mensaje);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }).start();
        }
    }
}
