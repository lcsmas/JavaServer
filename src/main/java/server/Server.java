package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    static Logger logger = Logger.getGlobal();
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(0)) {
            logger.log(Level.INFO, """
                    
                    Server address :    %s
                    Server port :       %d
                    """.formatted(server.getInetAddress().getHostName(), server.getLocalPort()));

            while(true) {
                Socket socket = server.accept();
                InputStream in = socket.getInputStream();

                new Thread(()-> {
                    try {
                        byte[] buffer = new byte[1024];
                        int read;
                        while((read = in.read(buffer)) != -1) {
                            logger.log(Level.INFO, String.valueOf(read));
                            logger.log(Level.INFO, new String(buffer, 0, read));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
