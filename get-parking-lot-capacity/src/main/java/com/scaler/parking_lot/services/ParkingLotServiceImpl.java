package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidParkingLotException;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.ParkingLotRepository;

import java.util.*;

public class ParkingLotServiceImpl implements ParkingLotService {
    private ParkingLotRepository repository;

    public ParkingLotServiceImpl(ParkingLotRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<ParkingFloor, Map<String, Integer>> getParkingLotCapacity(long parkingLotId, List<Long> parkingFloorIds, List<VehicleType> inputVehicleTypes) throws InvalidParkingLotException, InvalidParkingLotException {
       Optional<ParkingLot> parkingLotOptional = repository.getParkingLotById(parkingLotId);
        if(parkingLotOptional.isEmpty()){
            throw new InvalidParkingLotException("Invalid Parking Lot Id , Please try again...");
        }

        ParkingLot parkingLot = parkingLotOptional.get();
        Map<ParkingFloor, Map<String, Integer>> map = new HashMap<>();
        Set<Long> parkingFloorIdSet = (parkingFloorIds == null) ? new HashSet<>(): new HashSet<>(parkingFloorIds);

        if(inputVehicleTypes == null || inputVehicleTypes.isEmpty()){
            inputVehicleTypes = Arrays.asList(VehicleType.values());
        }

        for(ParkingFloor parkingFloor : parkingLot.getParkingFloors()){
            if(parkingFloorIdSet.size() > 0 && !parkingFloorIdSet.contains(parkingFloor.getId())){
                continue;
            }
            Map<String, Integer> vehicleTypeMap = new HashMap<>();
            for(VehicleType vehicleType : inputVehicleTypes){
                vehicleTypeMap.put(vehicleType.name(), calculateAvailableSpotsByVehicleType(parkingFloor, vehicleType));
            }
            map.put(parkingFloor, vehicleTypeMap);
        }
        return map;
    }

    public  int calculateAvailableSpotsByVehicleType(ParkingFloor parkingFloor, VehicleType vehicleType){
      int count = 0;
      if(parkingFloor == null || vehicleType == null || !parkingFloor.getStatus().equals(FloorStatus.OPERATIONAL)){
          return count;
      }

      for (ParkingSpot spot : parkingFloor.getSpots()) {
          if(spot.getSupportedVehicleType().equals(vehicleType) && spot.getStatus().equals(ParkingSpotStatus.AVAILABLE)){
              count++;
          }
      }
      return count;
}
}
