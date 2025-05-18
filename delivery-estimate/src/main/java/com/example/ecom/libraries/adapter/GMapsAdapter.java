package com.example.ecom.libraries.adapter;

import com.example.ecom.libraries.GoogleMapsApi;
import com.example.ecom.libraries.models.GLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GMapsAdapter implements MapsApiAdapter{
    @Autowired
    GoogleMapsApi mapsApi;

    @Override
    public int estimate(GLocation src, GLocation dest) {
        return mapsApi.estimate(src,dest);
    }
}
