package com.example.ecom.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Preference extends BaseModel{
    private String category;
    private String description;
    private Date createdAt;
}
