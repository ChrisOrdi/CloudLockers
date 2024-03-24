package com.avans.cloudlocker.cloudlocker.server.file;

import java.awt.*;
import java.io.*;
import java.text.MessageFormat;

public class Edit {
    public String editFile(String filepath) throws IOException {
        if (filepath.isEmpty()) {
            return "Filepath is empty.";
        }

        var file = new File(filepath);

        if (!file.exists()) {
            return "Could not get the file.";
        }

        if (!Desktop.isDesktopSupported()) {
            return "Can not open file on desktop.";
        }

        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);

        return MessageFormat.format("File {0} is now locked for modification.", file.getName());
    }
}
