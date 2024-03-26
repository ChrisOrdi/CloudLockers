package com.avans.cloudlocker.cloudlocker.server.directory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileStorageTests {

    private static final String TEST_DIRECTORY = "C:\\Temp\\TestDirectory";

    @BeforeAll
    static void setUp() throws IOException {
        // Create a test directory and add files for testing
        Files.createDirectories(Paths.get(TEST_DIRECTORY));
        Path file1 = Paths.get(TEST_DIRECTORY, "file1.txt");
        Path file2 = Paths.get(TEST_DIRECTORY, "file2.txt");

        if (!Files.exists(file1)) {
            Files.createFile(file1);
        }

        if (!Files.exists(file2)) {
            Files.createFile(file2);
        }
    }

    @Test
    void testSyncWithCloudStorage() throws IOException {
        // Arrange
        FileStorage fileStorage = new FileStorage();

        // Act
        String result = fileStorage.syncWithCloudStorage(TEST_DIRECTORY);

        // Assert
        assertTrue(result.startsWith("Copied"));
    }

    @Test
    void testSyncWithInvalidPath() throws IOException {
        // Arrange
        FileStorage fileStorage = new FileStorage();
        String invalidPath = "";

        // Act
        String result = fileStorage.syncWithCloudStorage(invalidPath);

        // Assert
        assertEquals("Directory path is empty.", result);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Path testDirectory = Paths.get(TEST_DIRECTORY);

        try (var stream = Files.walk(testDirectory)) {
            stream
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        Files.deleteIfExists(testDirectory);
    }
}