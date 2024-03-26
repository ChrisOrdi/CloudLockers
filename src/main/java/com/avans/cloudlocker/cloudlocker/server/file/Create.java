package com.avans.cloudlocker.cloudlocker.server.file;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Create {
    public String createFile(String directoryName, String fileName) {
        String userHomeFolder = System.getProperty("user.home");
        String directoryPath = userHomeFolder + "/Desktop/" + directoryName;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                return MessageFormat.format("Could not create directory: {0}", directory.getPath());
            }
        }

        File file = new File(directory, fileName);
        try {
            if (file.createNewFile()) {
                return MessageFormat.format("File created: {0}", file.getPath());
            } else {
                return MessageFormat.format("File already exists: {0}", file.getPath());
            }
        } catch (IOException e) {
            return MessageFormat.format("Could not create file: {0}", file.getPath());
        }
    }
}
