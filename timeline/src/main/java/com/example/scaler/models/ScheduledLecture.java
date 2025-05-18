package com.example.scaler.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ScheduledLecture extends BaseModel{
    @ManyToOne
    private Lecture lecture;
    @ManyToOne
    private Batch batch;
    private Date lectureStartTime;
    private Date lectureEndTime;
    private String lectureLink;
}
