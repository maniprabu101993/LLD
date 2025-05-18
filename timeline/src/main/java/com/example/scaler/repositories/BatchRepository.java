package com.example.scaler.repositories;

import com.example.scaler.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Long> {
}
