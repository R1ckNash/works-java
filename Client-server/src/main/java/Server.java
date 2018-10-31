import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws InterruptedException {

        ServerSocket server = null;
        String clientName;
        try {

            server = new ServerSocket(3345);

            Socket client = server.accept();

            System.out.print("Connection accepted.");
            System.out.println();


            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream created");

            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            clientName = in.readUTF();

            while(!client.isClosed()){

                System.out.println("Server reading from channel");

                String entry = in.readUTF();

                System.out.println("READ from " + clientName + " message - " + entry);

                System.out.println("Server try writing to channel");

                if (entry.equalsIgnoreCase("quit")){

                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - " + entry);
                    out.flush();
                    Thread.sleep(3000);
                    break;
                }

                out.writeUTF("Server reply - " + entry);
                out.flush();
                System.out.println("Server Wrote message to " + clientName);


            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");


            in.close();
            out.close();

            client.close();
            server.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            IoUtils.closeQuietly(server);
        }
    }
}