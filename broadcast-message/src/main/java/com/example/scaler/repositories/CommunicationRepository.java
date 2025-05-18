package com.example.scaler.repositories;

import com.example.scaler.models.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication,Long> {
}
