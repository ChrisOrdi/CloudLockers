package com.avans.cloudlocker.cloudlocker.server;

import com.avans.cloudlocker.cloudlocker.server.directory.Clear;
import com.avans.cloudlocker.cloudlocker.server.directory.Create;
import com.avans.cloudlocker.cloudlocker.server.directory.FileStorage;
import com.avans.cloudlocker.cloudlocker.server.file.Edit;
import com.avans.cloudlocker.cloudlocker.server.file.Delete;
import com.avans.cloudlocker.cloudlocker.server.file.Receive;

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
                                var endpoint = new Receive();
                                var result = endpoint.receiveFile(dataInputStream);

                                LOGGER.info(result);
                            }
                            break;
                        case "create":
                            if (parts.length == 2) {
                                var endpoint = new Create();
                                var result = endpoint.createDirectory(parts[1]);

                                LOGGER.info(result);
                            }
                            break;
                        case "createFile":
                            if (parts.length == 3) {
                                var endpoint = new com.avans.cloudlocker.cloudlocker.server.file.Create();
                                var result = endpoint.createFile(parts[1], parts[2]); // parts[1] should be directory, parts[2] should be filename

                                LOGGER.info(result);
                            } else {
                                LOGGER.warning("Invalid createFile command format. Use: createFile <directory> <filename>");
                            }
                            break;
                        case "delete":
                            if (parts.length == 2) {
                                var endpoint = new Delete();
                                var result = endpoint.deleteFile(parts[1]);

                                LOGGER.info(result);
                            } else {
                                LOGGER.warning("Invalid delete command format. Use: delete <filePath>");
                            }
                            break;
                        case "clearDirectory":
                            if (parts.length == 2) {
                                var endpoint = new Clear();
                                var result = endpoint.clearDirectory(parts[1]);

                                LOGGER.info(result);
                            } else {
                                LOGGER.warning("Invalid command format. Use: clearDirectory <directoryPath>");
                            }
                            break;
                        case "editFile":
                            if (parts.length == 2) {
                                var endpoint = new Edit();
                                var result = endpoint.editFile(parts[1]);

                                LOGGER.info(result);
                            } else {
                                LOGGER.warning("Invalid command format. Use: editFile <filePath>");
                            }
                            break;
                        case "syncWithCloudStorage":
                            if (parts.length == 2) {
                                var endpoint = new FileStorage();
                                var result = endpoint.syncWithCloudStorage();

                                LOGGER.info(result);
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
    }
}
