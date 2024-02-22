package com.avans.cloudlocker.cloudlocker.service;


import ch.qos.logback.core.net.server.Client;
import com.avans.cloudlocker.cloudlocker.model.CloudLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CloudLockerService {
    Logger logger = Logger.getLogger(getClass().getName());

    private int connection = 1;

    @Autowired
    private final MongoTemplate mongoTemplate;

    public CloudLockerService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void getClientState() {
        if (connection > 1) {
            logger.info("Client State werkt niet");
        }
        else {
            logger.info("Client State werkt");
        }
    }

    public void getAllClients() {
        // Voorbeeld van het ophalen van gegevens uit MongoDB
        // Dit is een placeholder, je moet dit vervangen met je eigen logica
        List<Client> clients = mongoTemplate.findAll(Client.class);
        // Verwerk of retourneer de opgehaalde gegevens
    }

    public void getAClient() {
        List<Client> clients = mongoTemplate.findAll(Client.class);
    }


    public CloudLocker addClient(CloudLocker client) {
        return mongoTemplate.save(client);
    }
}
