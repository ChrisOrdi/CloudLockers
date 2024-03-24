package com.avans.cloudlocker.cloudlocker.server;

import com.avans.cloudlocker.cloudlocker.server.directory.Clear;
import com.avans.cloudlocker.cloudlocker.server.file.Delete;

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

                    LOGGER.info("Client: " + command);

                    // Split command for method and arguments
                    String[] parts = command.split(" ", 3);
                    String action = parts[0];

                    switch (action) {
                        case "exit":
                            return; // End the thread
                        case "upload":
                            if (parts.length > 1) {
                                receiveFile(dataInputStream);
                            }
                            break;
                        case "create":
                            if (parts.length == 2) {
                                createDirectory(parts[1]);
                            }
                            break;
                        case "createFile":
                            if (parts.length == 3) {
                                createFile(parts[1], parts[2]); // parts[1] should be directory, parts[2] should be filename
                            } else {
                                LOGGER.warning("Invalid createFile command format. Use: createFile <directory> <filename>");
                            }
                            break;
                        case "delete":
                            if (parts.length == 2) {
                                String filepath = parts[1];
                                var endpoint = new Delete();
                                String result = endpoint.deleteFile(filepath);
                                LOGGER.info(result);
                            } else {
                                LOGGER.warning("Invalid delete command format. Use: delete <filepath>");
                            }
                            break;
                        case "clearDirectory":
                            if (parts.length == 2) {
                                String filepath = parts[1];
                                var endpoint = new Clear();
                                String result = endpoint.clearDirectory(filepath);
                                LOGGER.info(result);
                            } else {
                                LOGGER.warning("Invalid command format. Use: clearDirectory <directoryPath>");
                            }
                            break;
                        default:
                            LOGGER.warning("Unknown command received: " + command);
                            break;
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

        private void createFile(String directoryName, String fileName) {
            String userHomeFolder = System.getProperty("user.home");
            String directoryPath = userHomeFolder + "/Desktop/" + directoryName;
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if (!directory.mkdir()) {
                    LOGGER.severe("Could not create directory: " + directory.getPath());
                    return;
                }
            }

            File file = new File(directory, fileName);
            try {
                if (file.createNewFile()) {
                    LOGGER.info("File created: " + file.getPath());
                } else {
                    LOGGER.info("File already exists: " + file.getPath());
                }
            } catch (IOException e) {
                LOGGER.severe("Could not create file: " + file.getPath());
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
                while (totalRead < fileSize && (read = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalRead))) != -1) {
                    bos.write(buffer, 0, read);
                    totalRead += read;
                }
                bos.flush();
            }
            LOGGER.info("File " + fileName + " received.");
        }
    }
}
