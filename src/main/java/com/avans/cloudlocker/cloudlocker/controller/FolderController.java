package com.avans.cloudlocker.cloudlocker.controller;

import com.avans.cloudlocker.cloudlocker.model.FolderDocument;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderController {


    @GetMapping("listOfAllFolders")
    public List<FolderDocument> getListOfAllFolders(){
        return getListOfAllFolders();
    }

    @GetMapping("{foldername}")
    public String getFile(@PathVariable String foldername, @PathVariable String filename){

        return "File returned for folder: " + foldername +", file: " + filename;
    }

    // get the state of the client

    @PostMapping("Folder")
    public String createNewFolder(@PathVariable String foldername, @PathVariable String filename, @PathVariable String username)
    { // moet FileDocument zijn ipv String
        // TODO: Logica om toegang te geven
        return "File received for folder: " + foldername + ", file: " + filename + ", user: " + username;
    }

    @DeleteMapping("{foldername}")
    public String deleteFolder(@PathVariable String foldername)
    {
        return "File Deletion succesfull";
    }

    @DeleteMapping("{foldername}/{username}")
    public String deleteAccessRights(@PathVariable String foldername, @PathVariable String filename, @PathVariable String username)
    {
        return "Access right removed for user: " + username + " for folder: " + foldername;
    }
}
