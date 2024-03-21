package com.avans.cloudlocker.cloudlocker.controller;

import com.avans.cloudlocker.cloudlocker.model.FileDocument;
import com.avans.cloudlocker.cloudlocker.service.FileService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;
    private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("{foldername}/{filename}")
    public ResponseEntity<String> getFile(@PathVariable String foldername, @PathVariable String filename) {
        logger.info("Retrieving file from folder: {}, filename: {}");
        // Voeg logica toe om bestand op te halen
        return ResponseEntity.ok("File returned for folder: " + foldername + ", file: " + filename);
    }

    @PostMapping("{foldername}/{filename}/{username}")
    public ResponseEntity<String> handleAccess(@PathVariable String foldername, @PathVariable String filename, @PathVariable String username) {
        logger.info("Handling access for folder: {}, file: {}, user: {}");
        // Voeg logica toe om toegang te verwerken
        return ResponseEntity.ok("File received for folder: " + foldername + ", file: " + filename + ", user: " + username);
    }

    @PostMapping("{foldername}")
    public ResponseEntity<String> uploadFile(@PathVariable String foldername) {
        logger.info("Uploading file to folder: {}");
        // Voeg logica toe om bestand te uploaden
        return ResponseEntity.ok("Upload successful");
    }

    @PutMapping("{foldername}/{filename}")
    public ResponseEntity<String> updateFile(@PathVariable String foldername, @PathVariable String filename) {
        logger.info("Updating file in folder: {}, filename: {}");
        // Voeg logica toe om bestand bij te werken
        return ResponseEntity.ok("Update successful");
    }

    @DeleteMapping("{foldername}/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String foldername, @PathVariable String filename) {
        logger.info("Deleting file from folder: {}, filename: {}");
        // Voeg logica toe om bestand te verwijderen
        return ResponseEntity.ok("File deletion successful");
    }

    @DeleteMapping("{foldername}/{filename}/{username}")
    public ResponseEntity<String> deleteAccessRights(@PathVariable String foldername, @PathVariable String filename, @PathVariable String username) {
        logger.info("Removing access rights for folder: {}, file: {}, user: {}");
        // Voeg logica toe om toegangsrechten te verwijderen
        return ResponseEntity.ok("Access right removed for user: " + username);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileService.storeFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + file.getOriginalFilename());
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            byte[] data = fileService.loadFile(filename);
            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the file: " + id);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        List<String> files = fileService.listAllFiles();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getFileById(@PathVariable String id) {
        String file = fileService.getFileById(id);
        if (file != null) {
            return ResponseEntity.ok("File retrieved successfully: " + file);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found with id: " + id);
        }
    }

}
