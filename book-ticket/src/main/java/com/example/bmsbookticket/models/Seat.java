package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel{
    private String name;
    SeatType seatType;
    @ManyToOne
    Screen screen;
}
