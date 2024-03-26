package com.avans.cloudlocker.cloudlocker.server.file;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

public class Receive {
    public String receiveFile(DataInputStream dataInputStream) throws IOException {
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
        return MessageFormat.format("File {0} received.", fileName);
    }
}
