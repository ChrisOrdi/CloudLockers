package com.avans.cloudlocker.cloudlocker.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document // @document wordt gebruikt om een klasse te mappen naar een noSQl database
public class CloudLocker {

    private String filler;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public CloudLocker() {
    }
}
