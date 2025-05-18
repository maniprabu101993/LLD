package com.example.bmsbookticket.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Data
@Entity
public class Screen extends BaseModel {
    private String name;

    @OneToMany(mappedBy = "screen",fetch = FetchType.EAGER)
    private List<Seat> seats;

    @Enumerated(EnumType.ORDINAL)
    private ScreenStatus status;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Feature> features;
}
