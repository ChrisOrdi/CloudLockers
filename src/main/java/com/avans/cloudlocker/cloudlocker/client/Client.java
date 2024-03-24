package com.avans.cloudlocker.cloudlocker.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static DataOutputStream dataOutputStream = null;
    /*
    FileInputStream en FileOutputStream in combinatie met BufferedInputStream
    en BufferedOutputStream voor het verwerken van bestandsdata.
     */
    // TODO Implementeer een mechanisme om te controleren of een bestand op de client
    //  en server identiek is. Dit kan met behulp van checksums of hashes van de bestanden.
    //  Alleen bestanden die
    //  gewijzigd zijn of niet aanwezig zijn op de server moeten worden verzonden.

    // TODO ondersteuning voor grote bestanden: Zorg ervoor dat je systeem
    //  efficiënt kan omgaan met grote bestanden.
    //  Je kunt grote bestanden in chunks opdelen en deze
    //  afzonderlijk verzenden en weer samenvoegen.

    // TODO Netwerkfoutenafhandeling: Breid je code uit met
    //  robuustere foutafhandelingsmechanismen voor situaties
    //  waarin netwerkverbindingen worden verbroken of bestandsoverdrachten mislukken.


    // TODO Code-uitleg en documentatie: Zorg ervoor dat
    //  je code goed gedocumenteerd is, met duidelijke uitleg
    //  over hoe elke component werkt en hoe het samenwerkt binnen het systeem.


    private static DataInputStream dataInputStream = null;
    private static DataOutputStream dataOutputStream = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5101)) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.print("input> ");
                String input = scanner.nextLine();
                String[] parts = input.split(" ", 2);
                String command = parts[0];

                switch (command.toLowerCase()) {
                    case "exit":
                        dataOutputStream.writeUTF(input);
                        return;  // Exit the program
                    case "upload":
                        if (parts.length > 1) {
                            dataOutputStream.writeUTF("upload");
                            uploadFile(parts[1], dataOutputStream);
                        } else {
                            System.out.println("Upload command requires a file path.");
                        }
                        break;
                    default:
                        dataOutputStream.writeUTF(input);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private static void uploadFile(String filePath, DataOutputStream dataOutputStream) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        dataOutputStream.writeUTF(file.getName());
        dataOutputStream.writeLong(file.length());

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = bis.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, read);
            }
            dataOutputStream.flush();
        }
        System.out.println("File " + filePath + " has been sent.");
    }
}
