package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Vehicle;

import java.util.*;

public class VehicleRepositoryImpl implements VehicleRepository{

    private Map<Long, Vehicle> vehicleMap;
    private static long id = 0;

    public VehicleRepositoryImpl() {
        this.vehicleMap = new HashMap<>();
    }

    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber) {
        return this.vehicleMap.values().stream().filter(vehicle -> vehicle.getRegistrationNumber().equals(registrationNumber)).findFirst();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        if(vehicle.getId() == 0){
            vehicle.setId(id++);
        }
        this.vehicleMap.put(vehicle.getId(), vehicle);
        return vehicle;
    }
}
