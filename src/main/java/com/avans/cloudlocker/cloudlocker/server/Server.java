package com.avans.cloudlocker.cloudlocker.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final int portId = 5101;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        try (ServerSocket serverSocket = new ServerSocket(portId)) {
            LOGGER.info("Listening to port: " + portId);
            LOGGER.info("Initialization Time: " + now.toString() +
                    "\nMax Memory: " + maxMemory + " bytes" +
                    "\nAllocated Memory: " + allocatedMemory + " bytes" +
                    "\nFree Memory: " + freeMemory + " bytes" +
                    "\nAvailable Processors: " + runtime.availableProcessors());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info(clientSocket + " connected");
                LOGGER.info("Initialization Time: " + now.toString() +
                        "\nMax Memory: " + maxMemory + " bytes" +
                        "\nAllocated Memory: " + allocatedMemory + " bytes" +
                        "\nFree Memory: " + freeMemory + " bytes" +
                        "\nAvailable Processors: " + runtime.availableProcessors());
                new ClientHandler(clientSocket).start();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Server exception", e);
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

                String message;
                while (true) {
                    message = dataInputStream.readUTF();
                    LOGGER.info("Message from client: " + message);
                    if (message.equalsIgnoreCase("exit()")) {
                        break;
                    }
                }
                clientSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Connection error", e);
            }
        }
    }
}
