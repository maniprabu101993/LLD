package com.example.bmsbookticket.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;
    private Date startTime;
    private Date endTime;

    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Feature> features;
    @ManyToOne
    private Screen screen;
}
