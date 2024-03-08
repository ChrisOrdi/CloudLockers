package com.avans.cloudlocker.cloudlocker.controller;

import com.avans.cloudlocker.cloudlocker.model.FileDocument;
import com.avans.cloudlocker.cloudlocker.service.FileService;
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

    Logger logger = Logger.getLogger(getClass().getName());


    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // Get Mappings

    @GetMapping("{foldername}/{filename}")
    public String getFile(@PathVariable String foldername, @PathVariable String filename){

        return "File returned for folder: " + foldername +", file: " + filename;
    }

    // get the state of the client

    @PostMapping("{foldername}/{filename}/{username}")
    public String handleAccess(@PathVariable String foldername, @PathVariable String filename, @PathVariable String username)
    { // moet FileDocument zijn ipv String
        // TODO: Logica om toegang te geven
        return "File received for folder: " + foldername + ", file: " + filename + ", user: " + username;
    }

    @PostMapping("{foldername}")
    public String uploadFile(@PathVariable String foldername) {

    return "Upload successfull";
        // Gebruik dit endpoint om een nieuw bestand naar een specifieke folder te uploaden. Dit valt onder het uploaden van nieuwe bestanden naar de server.
    }

    @PutMapping("{foldername}/{filename}")
    public String updateFile(@PathVariable String foldername, @PathVariable String filename)
    {
        return "Update successfull";
    }

    @DeleteMapping("{foldername}/{filename}")
    public String deleteFile(@PathVariable String foldername, @PathVariable String filename)
    {
        return "File Deletion succesfull";
    }

    @DeleteMapping("{foldername}/{filename}/{username}")
    public String deleteAccessRights(@PathVariable String foldername, @PathVariable String filename, @PathVariable String username)
    {
        return "Access right removed for user: " + username;
    }

}
