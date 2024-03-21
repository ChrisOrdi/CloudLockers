package com.avans.cloudlocker.cloudlocker.service;


import com.avans.cloudlocker.cloudlocker.model.FileDocument;
import com.avans.cloudlocker.cloudlocker.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FileService {

    Logger logger = Logger.getLogger(getClass().getName());

    private final FileRepository fileRepository;

    // Veronderstel dat bestanden lokaal worden opgeslagen in deze directory
    private final Path rootLocation = Paths.get("filestorage");

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String saveFile(FileDocument file) {
        // Logica voor het opslaan van bestandsinformatie
     //   return fileRepository.save(file);
        return "blabla";
    }

    public List<String> getAllFiles() {
        // Logica voor het ophalen van alle bestanden
        return fileRepository.findAll();
    }

    public FileDocument getFileById(String id) {
        Integer intId = Integer.parseInt(id); // Zet de String om naar Integer
        Optional<FileDocument> result = fileRepository.findById(intId);
        return result.orElse(null); // Geeft FileDocument terug of null als het niet wordt gevonden
    }



    public void deleteFile(String id) {
        // Logica voor het verwijderen van een bestand
        fileRepository.deleteById(id);
    }

    public void storeFile(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        // Voeg logica toe om de bestandsmetadata op te slaan in MongoDB
    }

    public byte[] loadFile(String filename) throws IOException {
        return Files.readAllBytes(rootLocation.resolve(filename));
        // Voeg logica toe om de bestandsmetadata te laden van MongoDB
    }

    public List<String> listAllFiles() {
        // Haal alle bestandsmetadata op uit MongoDB
        return fileRepository.findAll();
    }

    // Andere benodigde methoden zoals deleteFile, updateFileMetadata, etc.
}
