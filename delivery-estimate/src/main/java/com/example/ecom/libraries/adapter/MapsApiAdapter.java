package com.example.ecom.libraries.adapter;

import com.example.ecom.libraries.models.GLocation;

import java.util.Random;

public interface MapsApiAdapter {
    public int estimate(GLocation src, GLocation dest);
}
