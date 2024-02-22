package com.avans.cloudlocker.cloudlocker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudLockerController {

    private int connection = 1;

    // Get Mappings

    @GetMapping("/requestClientState")
    public void getClientState() {
        if (connection > 1) {
            System.out.println("Client State werkt niet");
        }
        else {
            System.out.println("Client State werkt");
        }
    }

    // Post Mappings


    // Put mappings


    // Delete mappings
}
