package com.avans.cloudlocker.cloudlocker.client;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private  static DataOutputStream dataOutputStream = null;
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
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost",5101)) { // willen we met elkaar communiceren moet het dus Socket socket = new Socket("192.168.1.5", 5101);
            dataInputStream = new DataInputStream(socket.getInputStream());  // zijn ipv localhost en dan de port.
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.print("input> ");
                String message = scanner.nextLine();
                dataOutputStream.writeUTF(message);
                if(message.equalsIgnoreCase("exit()"))
                    break;
                /*
                case statement:
                exit() : applicatie stopt
                upload() : start upload proces, naam van bestand en bestandstype moet
                meegegeven worden.
                download() : start download proces, naam van bestand en bestandstype moet
                meegegeven worden.
                delete() : start delete proces, naam van bestand en bestandstype moet
                meegegeven worden.


                 */
            }

        }catch (Exception e){
            System.out.println(e.toString());
            }
        }
    }