package com.avans.cloudlocker.cloudlocker.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document // @document wordt gebruikt om een klasse te mappen naar een noSQl database
public class FileDocument {

    @Id
    private String id;
    private String name;
    private long size; // Grootte van het bestand

    // Constructors, getters, setters
    public FileDocument() { // no args cons
    }

    public FileDocument(String id, String name, long size) { // all args constructor
        this.id = id;
        this.name = name;
        this.size = size;
    }

}
