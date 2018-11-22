import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static int PORT;

    public static void main(String[] args){

        Logger logger = Logger.getLogger(Server.class.getName());
        ServerSocket server = null;
        String clientName = null;
        logger.info("Which PORT to listen?");
        Scanner scanner = new Scanner(System.in);
        PORT = scanner.nextInt();

        try {
            server = new ServerSocket(PORT);
            Socket client = server.accept();
            logger.info("Connection accepted.");

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            logger.info("DataOutputStream created");

            DataInputStream in = new DataInputStream(client.getInputStream());
            logger.info("DataInputStream created");

            out.writeUTF("Welcome to the server!" + "\n" + "Available commands:" + "\n" + "Set username - @name Vasya" + "\n" + "Send message - Hello" + "\n" + "Disconnect - @quit");
            out.flush();




            while(!client.isClosed()){

                logger.info("Server reading from channel");
                String entry = in.readUTF();

                if(entry.startsWith("@name")) {
                    clientName = entry;
                    clientName = clientName.replaceFirst("@name ", "");
                    out.writeUTF("Nice to meet you " + clientName);
                    out.flush();

                }else{

                    logger.log(Level.INFO,"READ from {0}. ", clientName + " message - " + entry);
                    logger.info("Server try writing to channel");
                    out.writeUTF("Server reply - " + entry);
                    out.flush();
                    logger.log(Level.INFO,"Server Wrote message to {0}.", clientName);
                }


                if (entry.equalsIgnoreCase("@quit")){
                    logger.info("Client initialize connections suicide ...");
                    break;
                }
            }

            logger.info("Client disconnected");
            logger.info("Closing connections & channels.");

            in.close();
            out.close();
            client.close();
            server.close();

            logger.info("Closing connections & channels - DONE.");
        } catch (IOException e) {
           logger.info(e.getMessage());
        } finally{
            IoUtils.closeQuietly(server);
        }
    }
}