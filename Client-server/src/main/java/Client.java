import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;
        String clientName;

        try {

            socket = new Socket("localhost", 3345);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());


            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = out & reading channel = in initialized.");
            System.out.println("Enter your name:");
            clientName = br.readLine();
            out.writeUTF(clientName);
            out.flush();

            while(!socket.isOutputShutdown()){

                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);

                    System.out.println("Client " + clientName + " start writing in chat...");

                    String clientCommand = br.readLine();

                    out.writeUTF(clientCommand);
                    out.flush();

                    System.out.println("Client sent message " + clientCommand + " to server.");
                    Thread.sleep(1000);

                    if(clientCommand.equalsIgnoreCase("quit")){

                        System.out.println("Client disconnect from channel");
                        Thread.sleep(2000);


                        System.out.println("reading...");
                        String readMessage = in.readUTF();
                        System.out.println(readMessage);


                        break;
                    }

                    System.out.println("Client sent message & start waiting for data from server...");
                    Thread.sleep(2000);


                    System.out.println("reading...");
                    String readMessage = in.readUTF();
                    System.out.println(readMessage);

            }
            System.out.println("Closing connections & channels on clientSide - DONE.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            IoUtils.closeQuietly(socket);
        }
    }
}