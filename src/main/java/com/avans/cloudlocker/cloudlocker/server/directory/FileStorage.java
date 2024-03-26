package com.avans.cloudlocker.cloudlocker.server.directory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FileStorage {
    private static final String cloudStoragePath = "C:\\Temp\\CloudLockerStorage";
    public static String localStoragePath;

    public String syncWithCloudStorage(String directoryPath) throws IOException {
        if (directoryPath.isEmpty())
            return "Directory path is empty.";

        var path = Paths.get(directoryPath);

        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }

        localStoragePath = directoryPath;

        var result = configureCloudStorage();

        if (result == -1) {
            return "Failed to sync with the storage directory.";
        }
        else if (result == 0) {
            return "There is nothing to sync as the storage directory is empty.";
        }
        else {
            return MessageFormat.format("Copied {0} files to: {1}", result, localStoragePath);
        }
    }

    private int configureCloudStorage() throws IOException {
        var cloudPath = Paths.get(cloudStoragePath);
        var localPath = Paths.get(localStoragePath);
        var fileCount = new AtomicInteger();

        if (Files.notExists(cloudPath)) {
            Files.createDirectories(cloudPath);
        }

        try (var stream = Files.list(cloudPath)) {
            List<Path> fileList = stream.toList();
            if (!fileList.isEmpty()) {
                try(var pathStream = Files.walk(cloudPath)){
                    pathStream.forEach(source -> {
                        var target = localPath.resolve(cloudPath.relativize(source));
                        try {
                            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                            fileCount.getAndIncrement();
                        } catch (IOException e) {
                            System.err.println("Failed to copy: " + source);
                            fileCount.getAndSet(-1);
                        }
                    });
                }
            } else {
                fileCount.getAndSet(0);
            }
        }

        return fileCount.get();
    }
}
