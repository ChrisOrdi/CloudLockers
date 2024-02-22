package com.avans.cloudlocker.cloudlocker.repository;

import com.avans.cloudlocker.cloudlocker.model.CloudLocker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudLockerRepository extends JpaRepository<CloudLocker, Long> {
}
