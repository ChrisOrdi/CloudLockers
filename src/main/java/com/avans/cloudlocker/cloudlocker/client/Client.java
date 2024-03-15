package com.avans.cloudlocker.cloudlocker.client;
import java.io.*;
import java.net.Socket;

public class Client {


    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
 /*
    public void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void uploadFile(String filePath) throws IOException {
        sendCommand("UPLOAD");
        File file = new File(filePath);
        writer.println(file.getName());
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = socket.getOutputStream()) {
            byte[] bytes = new byte[4096];
            int count;
            while ((count = bis.read(bytes)) > 0) {
                os.write(bytes, 0, count);
            }
            os.flush();
            System.out.println("Bestand is geupload: " + filePath);
        }
    }

    public void sendCommand(String command) {
        writer.println(command);
    }

    public void stopConnection() throws IOException {
        if (reader != null) {
            reader.close();
        }
        if (writer != null) {
            writer.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Gebruik: java Client [upload] [bestandspad]");
            return;
        }

        String command = args[0];
        String filePath = args[1];

        Client client = new Client();

        try {
            client.startConnection("127.0.0.1", 6666);

            switch (command.toLowerCase()) {
                case "upload":
                    client.uploadFile(filePath);
                    break;
                default:
                    System.out.println("Ongeldig commando. Gebruik 'upload' om een bestand te uploaden.");
            }
        } catch (IOException e) {
            System.out.println("Er is een fout opgetreden: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                client.stopConnection();
            } catch (IOException e) {
                System.out.println("Fout bij het afsluiten van de verbinding: " + e.getMessage());
            }
        }
    }


     */

    // Binnen de Client class
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;

    public void startConnection(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void chatWithServer() throws IOException {
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Server: " + in.readLine());
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
        }
    }

    public void stopConnection() throws IOException {
        stdIn.close();
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        client.chatWithServer();
        client.stopConnection();
    }
}