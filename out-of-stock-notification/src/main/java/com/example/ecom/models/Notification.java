package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Notification extends BaseModel{
    @OneToOne
    private Product product;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private NotificationStatus status;
}
