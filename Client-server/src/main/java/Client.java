import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    public static int PORT;

    public static void main(String[] args) throws InterruptedException {
        Logger logger = Logger.getLogger(Server.class.getName());
        Socket socket = null;
        String clientName;

        logger.info("Which PORT to connect?");
        Scanner scanner = new Scanner(System.in);
        PORT = scanner.nextInt();

        try {
            socket = new Socket("localhost", PORT);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            logger.info("Client connected to socket." + "\n" + "Writing channel and reading channel - initialized " + "\n" + "Enter your name:");

            clientName = br.readLine();
            out.writeUTF(clientName);
            out.flush();

            while(!socket.isOutputShutdown()){

                logger.log(Level.INFO,"Client {0}.", clientName + " start writing in chat...");
                String clientCommand = br.readLine();

                out.writeUTF(clientCommand);
                out.flush();

                logger.log(Level.INFO,"Client {0}.", clientName + " sent message:  << " + clientCommand + " >> to server.");
                Thread.sleep(1000);

                    if(clientCommand.equalsIgnoreCase("quit")){
                        logger.info("Client disconnect from channel");
                        Thread.sleep(2000);
                        break;
                    }

                logger.info("Client sent message & start waiting for data from server...");
                Thread.sleep(2000);
                String readMessage = in.readUTF();
                logger.info(readMessage);
            }

            logger.info("Closing connections & channels on clientSide - DONE.");

        } catch (UnknownHostException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }finally{
            IoUtils.closeQuietly(socket);
        }
    }
}