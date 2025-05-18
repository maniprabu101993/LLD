package com.example.scaler.repositories;

import com.example.scaler.models.BatchLearner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchLearnerRepository extends JpaRepository<BatchLearner, Long> {
    List<BatchLearner> findAllByLearnerId(Long learnerId);
}
