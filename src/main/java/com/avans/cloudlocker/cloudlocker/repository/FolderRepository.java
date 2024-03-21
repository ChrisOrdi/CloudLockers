package com.avans.cloudlocker.cloudlocker.repository;

import com.avans.cloudlocker.cloudlocker.model.FolderDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<String, FolderDocument> {
}
