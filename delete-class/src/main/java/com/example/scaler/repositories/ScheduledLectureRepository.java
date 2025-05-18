package com.example.scaler.repositories;

import com.example.scaler.models.ScheduledLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledLectureRepository extends JpaRepository<ScheduledLecture, Long> {

    @Query(value = "SELECT * FROM scheduled_lecture WHERE batch_id = ?1", nativeQuery = true)
    List<ScheduledLecture> findScheduledLectureByBatchId(Long batchId);
}

