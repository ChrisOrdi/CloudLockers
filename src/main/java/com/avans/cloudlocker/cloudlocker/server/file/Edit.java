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

        if (!file.canWrite()) {
            return MessageFormat.format("Can not open file {0} as it is opened in another client.", file.getName());
        }

        if (!Desktop.isDesktopSupported()) {
            return "Can not open file on desktop.";
        }

        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);

        var modified = file.setWritable(false);

        if (modified) {
            return MessageFormat.format("File {0} is now locked for modification.", file.getName());
        } else {
            return MessageFormat.format("Could not lock file {0}.", file.getName());
        }
    }
}
