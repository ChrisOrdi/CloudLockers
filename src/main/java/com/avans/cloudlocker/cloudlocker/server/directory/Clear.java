package com.avans.cloudlocker.cloudlocker.server.directory;

import java.io.File;


public class Clear {
    public String clearDirectory(String directoryPath) {
        if (directoryPath.isEmpty())
            return "Directory path is empty";

        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory())
            return "Directory does not exist";

        File[] files = directory.listFiles();
        if (files == null)
            return "Failed to list files in directory";

        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    clearDirectory(file.getAbsolutePath());
                } else {
                    boolean deleted = file.delete();

                    if (!deleted)
                        return "Failed to delete file: " + file.getAbsolutePath();
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }

        return "All files in directory deleted successfully";
    }
}
