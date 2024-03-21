package com.avans.cloudlocker.cloudlocker.repository;

import com.avans.cloudlocker.cloudlocker.model.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<String, FileDocument> { // String en Integer moet nog aangepast worden.

    // hieronder kunnen we queries zetten
}
