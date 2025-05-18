package com.example.scaler.repositories;

import com.example.scaler.models.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
}
