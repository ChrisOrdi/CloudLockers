package com.avans.cloudlocker.cloudlocker.server;

import com.avans.cloudlocker.cloudlocker.server.directory.Clear;
import com.avans.cloudlocker.cloudlocker.server.file.Delete;
import com.avans.cloudlocker.cloudlocker.server.file.Edit;

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
            LOGGER.info("Initialization Time: " + now +
                    "\nMax Memory: " + maxMemory + " bytes" +
                    "\nAllocated Memory: " + allocatedMemory + " bytes" +
                    "\nFree Memory: " + freeMemory + " bytes" +
                    "\nAvailable Processors: " + runtime.availableProcessors());
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

                    if (command.equals("exit()")) {
                        break;
                    } else if (command.startsWith("upload")) {
                        receiveFile(dataInputStream);
                    } else if (command.startsWith("delete")) {
                        var parts = command.split(" ", 2);

                        if (parts.length == 2) {
                            var filepath = parts[1];
                            var endpoint = new Delete();

                            var result = endpoint.deleteFile(filepath);

                            LOGGER.info(result);
                        } else {
                            LOGGER.warning("Invalid delete command format. Use: delete {filepath}");
                        }
                    } else if (command.startsWith("clearDirectory")) {
                        var parts = command.split(" ", 2);

                        if (parts.length == 2) {
                            var filepath = parts[1];
                            var endpoint = new Clear();

                            var result = endpoint.clearDirectory(filepath);

                            LOGGER.info(result);
                        } else {
                            LOGGER.warning("Invalid command format. Use: clearDirectory {directoryPath}");
                        }
                    } else if (command.startsWith("edit")) {
                        var parts = command.split(" ", 2);

                        if (parts.length == 2) {
                            var filepath = parts[1];
                            var endpoint = new Edit();

                            var result = endpoint.editFile(filepath);

                            LOGGER.info(result);
                        } else {
                            LOGGER.warning("Invalid edit command format");
                        }
                    }
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Connection error", e);
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
