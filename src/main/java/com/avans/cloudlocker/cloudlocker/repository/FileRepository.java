package com.avans.cloudlocker.cloudlocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<String, Integer> { // String en Integer moet nog aangepast worden.

    // hieronder kunnen we queries zetten
}
