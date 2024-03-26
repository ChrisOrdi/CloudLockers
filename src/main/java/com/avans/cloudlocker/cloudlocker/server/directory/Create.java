package com.avans.cloudlocker.cloudlocker.server.directory;

import java.io.File;
import java.text.MessageFormat;

public class Create {
    public String createDirectory(String directoryName) {
        String userHomeFolder = System.getProperty("user.home");
        String desktopPath = userHomeFolder + "/Desktop/" + directoryName;
        File directory = new File(desktopPath);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                return MessageFormat.format("Directory created: {0}", directory.getPath());
            } else {
                return MessageFormat.format("Failed to create directory: {0}", directory.getPath());
            }
        } else {
            return MessageFormat.format("Directory already exists: {0}", directory.getPath());
        }
    }
}
