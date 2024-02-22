package com.avans.cloudlocker.cloudlocker.controller;

import ch.qos.logback.core.net.server.Client;
import com.avans.cloudlocker.cloudlocker.model.CloudLocker;
import com.avans.cloudlocker.cloudlocker.service.CloudLockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class CloudLockerController {


    @Autowired
    private final CloudLockerService cloudLockerService;

    Logger logger = Logger.getLogger(getClass().getName());

    private int connection = 1;

    public CloudLockerController(CloudLockerService cloudLockerService) {
        this.cloudLockerService = cloudLockerService;
    }

    // Get Mappings

    // get the state of the client
    @GetMapping("/requestClientState")
    public void getClientState() {
        cloudLockerService.getClientState();
    }

    // get a list of all clients
    @GetMapping("/getAllClients")
    public void getAllClients() {
        cloudLockerService.getAllClients();
    }

    // get a list of a clients
    @GetMapping("/getAClient")
    public void getAClient() {
        cloudLockerService.getAClient();
    }

    // Post Mappings

    // add a client
    @PostMapping
    public CloudLocker addClient(@RequestBody CloudLocker client) {
       return cloudLockerService.addClient(client);
    }

    // Put mappings


    // Delete mappings
}
