package com.avans.cloudlocker.cloudlocker.server.directory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class FileStorage {
    private static final String cloudStoragePath = "C:\\Temp\\CloudLockerStorage";
    public static String localStoragePath;

    public String syncWithCloudStorage(String directoryPath) throws IOException {
        if (directoryPath.isEmpty())
            return "Directory path is empty.";

        var path = Paths.get(directoryPath);

        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }

        configureCloudStorage();

        return MessageFormat.format("Files will now be written to: {0}.", path.getFileName());
    }

    private void configureCloudStorage() throws IOException {
        var path = Paths.get(cloudStoragePath);

        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }

        if(path.isNotEmpty()) {

        }
    }
}
