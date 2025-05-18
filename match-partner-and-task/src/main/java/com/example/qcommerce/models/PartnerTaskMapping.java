package com.example.qcommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PartnerTaskMapping extends BaseModel {
    @ManyToOne
    private Partner partner;
    @ManyToOne
    private Task task;
}
