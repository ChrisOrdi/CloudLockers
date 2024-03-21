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

        if (LOGGER.isLoggable(Level.INFO)) {
            long maxMemory = runtime.maxMemory();
            long allocatedMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();

            LOGGER.info("Listening to port: " + portId);
            LOGGER.info(String.format("Initialization Time: %s\nMax Memory: %d bytes\nAllocated Memory: %d bytes\nFree Memory: %d bytes\nAvailable Processors: %d",
                    now, maxMemory, allocatedMemory, freeMemory, runtime.availableProcessors()));
        }

        try (ServerSocket serverSocket = new ServerSocket(portId)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.info(clientSocket + " connected");
                new ClientHandler(clientSocket).start();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Server exception", e);
        }
    }


    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())) {
                while (true) {
                    String command = dataInputStream.readUTF();
                    LOGGER.info("Client stuurde bericht: " + command);

                    if (command.equals("exit()")) {
                        break;
                    } else if (command.startsWith("upload")) {
                        receiveFile(dataInputStream);
                    } else if (command.startsWith("create ")) {
                        createDirectory(command.substring(7)); // Verwijder "create " om de mapnaam te krijgen
                    }
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Connection error", e);
            }
        }

        private void createDirectory(String directoryName) {
            String userHomeFolder = System.getProperty("user.home");
            String desktopPath = userHomeFolder + "/Desktop/" + directoryName;
            File directory = new File(desktopPath);
            if (!directory.exists()) {
                if (directory.mkdir()) {
                    LOGGER.info("Directory created: " + directory.getPath());
                } else {
                    LOGGER.severe("Failed to create directory: " + directory.getPath());
                }
            } else {
                LOGGER.info("Directory already exists: " + directory.getPath());
            }
        }



        private void receiveFile(DataInputStream dataInputStream) throws IOException {
            String fileName = dataInputStream.readUTF();
            long fileSize = dataInputStream.readLong();
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                byte[] buffer = new byte[4096];
                int read = 0;
                long totalRead = 0;
                while (totalRead < fileSize && (read = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, fileSize - totalRead))) != -1) {
                    bos.write(buffer, 0, read);
                    totalRead += read;
                }
                bos.flush();
            }
            LOGGER.info("File " + fileName + " received.");
        }
    }
}