package com.example.qcommerce.utils;

import com.example.qcommerce.models.Partner;
import com.example.qcommerce.models.Task;

public class PartnerDistance implements Comparable<PartnerDistance> {
    private Partner partnerId;
    private Double distance;

    public PartnerDistance(Partner partnerId, Double distance) {
        this.partnerId = partnerId;
        this.distance = distance;
    }

    public Partner getPartnerId() {
        return partnerId;
    }

    public Double getDistance() {
        return distance;
    }

    public int compareTo(PartnerDistance o) {
        return this.distance.compareTo(o.getDistance());
    }

}
