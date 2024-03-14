package com.avans.cloudlocker.cloudlocker.repository;

import com.avans.cloudlocker.cloudlocker.model.FileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<FileDocument, String> { /* String omdat MongoDB ID's vaak als String worden gepresenteerd*/

    // hieronder kunnen we queries zetten
}
