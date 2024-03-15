package com.avans.cloudlocker.cloudlocker.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService pool; // Voor het beheren van meerdere clients

    /*

    public Server(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
    }

    public void start() {
        System.out.println("Server gestart op poort " + serverSocket.getLocalPort());
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (pool != null) {
                pool.shutdown();
            }
        } catch (IOException e) {
            System.out.println("Fout bij het sluiten van de server: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                String fileName;
                String command = input.readLine();
                switch (command) {
                    case "UPLOAD":
                        fileName = input.readLine();
                        receiveFile(socket, fileName);
                        break;
                    case "DOWNLOAD":
                        fileName = input.readLine();
                        sendFile(socket, fileName, output);
                        break;
                    case "DELETE":
                        fileName = input.readLine();
                        deleteFile(fileName, output);
                        break;
                    default:
                        output.println("Ongeldig commando");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Handler Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private void receiveFile(Socket socket, String fileName) {
            try (DataInputStream dis = new DataInputStream(socket.getInputStream());
                 FileOutputStream fos = new FileOutputStream(fileName)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = dis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("Bestand ontvangen: " + fileName);
            } catch (IOException e) {
                System.out.println("Ontvangen bestand Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private void sendFile(Socket socket, String fileName, PrintWriter output) {
            File file = new File(fileName);
            if (!file.exists()) {
                output.println("Bestand niet gevonden");
                return;
            }

            try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                 FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                while (fis.read(buffer) > 0) {
                    dos.write(buffer);
                }
                System.out.println("Bestand verzonden: " + fileName);
            } catch (IOException e) {
                System.out.println("Verzend bestand Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private void deleteFile(String fileName, PrintWriter output) {
            File file = new File(fileName);
            if (file.delete()) {
                System.out.println("Bestand verwijderd: " + fileName);
                output.println("Bestand verwijderd: " + fileName);
            } else {
                System.out.println("Kon bestand niet verwijderen: " + fileName);
                output.println("Fout bij het verwijderen van bestand");
            }
        }
    }

    public static void main(String[] args) {
        int port = 6666; // Stel een poortnummer in
        int poolSize = 10; // Aantal threads in de pool
        try {
            Server server = new Server(port, poolSize);
            server.start();
        } catch (IOException e) {
            System.out.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }


     */

    // Binnen de ClientHandler class van de server


        public void start(int port) throws IOException {
            serverSocket = new ServerSocket(port);
            System.out.println("Server gestart en luistert op poort " + port);

            try {
                while (true) {
                    new ClientHandler(serverSocket.accept()).start();
                }
            } finally {
                serverSocket.close();
            }
        }

        private static class ClientHandler extends Thread {
            private Socket clientSocket;
            private PrintWriter out;
            private BufferedReader in;

            public ClientHandler(Socket socket) {
                this.clientSocket = socket;
            }

            public void run() {
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        if ("knock-knock".equalsIgnoreCase(inputLine)) {
                            out.println("Who's there?");
                        } else {
                            out.println("You're supposed to say 'knock-knock'!");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public static void main(String[] args) throws IOException {
            Server server = new Server();
            server.start(6666);
        }
    }