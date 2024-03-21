package com.avans.cloudlocker.cloudlocker.server.file;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Delete {
    public String deleteFile(String filepath) throws IOException {
        if (filepath.isEmpty())
            return "Filepath is empty";

        var file = new File(filepath);

        if (!file.exists())
            return "File does not exist.";

        try {
            var succeeded = file.delete();

            if (succeeded)
                return MessageFormat.format("File {0} deleted.", file.getName());
            else
                return MessageFormat.format("Could not delete file {0}.", file.getName());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
