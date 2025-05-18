package com.scaler.parking_lot.strategies.assignment;

import com.scaler.parking_lot.models.*;

import java.util.Optional;

public class LeastSpotAssignmentStrategy implements SpotAssignmentStrategy {
    @Override
    public Optional<ParkingSpot> assignSpot(ParkingLot parkingLot, VehicleType vehicleType) {
        for (ParkingFloor floor : parkingLot.getParkingFloors()) {
            if (floor.getStatus() == FloorStatus.OPERATIONAL) {
                for (ParkingSpot spot : floor.getSpots()) {
                    if (spot.getStatus() == ParkingSpotStatus.AVAILABLE) {
                        if (spot.getSupportedVehicleType() == vehicleType) {
                            spot.setStatus(ParkingSpotStatus.OCCUPIED);
                            return Optional.of(spot);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
