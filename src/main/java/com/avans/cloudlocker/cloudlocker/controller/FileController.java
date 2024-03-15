package com.avans.cloudlocker.cloudlocker.controller;

import com.avans.cloudlocker.cloudlocker.model.FileDocument;
import com.avans.cloudlocker.cloudlocker.service.FileService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
