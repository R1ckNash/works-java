import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args){
        Logger logger = Logger.getLogger(Server.class.getName());
        ServerSocket server = null;
        String clientName;

        try {

            server = new ServerSocket(8080);

            Socket client = server.accept();

            logger.info("Connection accepted.");

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            logger.info("DataOutputStream created");
            DataInputStream in = new DataInputStream(client.getInputStream());
            logger.info("DataInputStream created");
            clientName = in.readUTF();

            while(!client.isClosed()){

                logger.info("Server reading from channel");

                String entry = in.readUTF();

                logger.log(Level.INFO,"READ from {0}. ", clientName + " message - " + entry);
                logger.info("Server try writing to channel");

                if (entry.equalsIgnoreCase("quit")){

                    logger.info("Client initialize connections suicide ...");
                    break;
                }
                out.writeUTF("Server reply - " + entry);
                out.flush();
                logger.log(Level.INFO,"Server Wrote message to {0}.", clientName);
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