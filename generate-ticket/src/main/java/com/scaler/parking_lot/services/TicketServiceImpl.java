package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidGateException;
import com.scaler.parking_lot.exceptions.InvalidParkingLotException;
import com.scaler.parking_lot.exceptions.ParkingSpotNotAvailableException;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.GateRepository;
import com.scaler.parking_lot.respositories.ParkingLotRepository;
import com.scaler.parking_lot.respositories.TicketRepository;
import com.scaler.parking_lot.respositories.VehicleRepository;
import com.scaler.parking_lot.strategies.assignment.SpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;

public class TicketServiceImpl implements TicketService {
    GateRepository gateRepo;
    ParkingLotRepository parkingRepo;
    TicketRepository ticketRepo;
    VehicleRepository vehicleRepository;
    SpotAssignmentStrategy spotassign;

    public TicketServiceImpl(GateRepository gateRepo, ParkingLotRepository parkingRepo, TicketRepository ticketRepo, VehicleRepository vehicleRepository, SpotAssignmentStrategy spotassign) {
        this.gateRepo = gateRepo;
        this.parkingRepo = parkingRepo;
        this.vehicleRepository = vehicleRepository;
        this.ticketRepo = ticketRepo;
        this.spotassign = spotassign;
    }

    @Override
    public Ticket generateTicket(int gateId, String registrationNumber, String vehicleType) throws InvalidGateException, InvalidParkingLotException, ParkingSpotNotAvailableException {
        Optional<Gate> optionalGate = this.gateRepo.findById(gateId);
        if (optionalGate.isEmpty()) {
            throw new InvalidGateException("Invalid gate id");
        }
        Gate gate = optionalGate.get();
        if(gate.getType().equals(GateType.EXIT)) {
            throw new InvalidGateException("Vehicle trying to enter from exit gate");
        }

        Vehicle vehicle;
        Optional<Vehicle> optionalVehicle = vehicleRepository.getVehicleByRegistrationNumber(registrationNumber);
        if (optionalVehicle.isEmpty()) {
            vehicle = new Vehicle();
            vehicle.setRegistrationNumber(registrationNumber);
            vehicle.setVehicleType(VehicleType.valueOf(vehicleType));
            vehicle = vehicleRepository.save(vehicle);
        } else {
            vehicle = optionalVehicle.get();
        }

        Optional<ParkingLot> parkingLotOptional = this.parkingRepo.getParkingLotByGateId(gateId);
        if (parkingLotOptional.isEmpty()) {
            throw new InvalidParkingLotException("Invalid parking lot id");
        }
        ParkingLot parkingLot = parkingLotOptional.get();

        Optional<ParkingSpot> parkingSpotOptional = spotassign.assignSpot(parkingLot, VehicleType.valueOf(vehicleType));
        if (parkingSpotOptional.isEmpty()) {
            throw new ParkingSpotNotAvailableException("No parking spot available");
        }
        ParkingSpot parkingSpot = parkingSpotOptional.get();

        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(new Date());
        ticket.setParkingSpot(parkingSpot);
        ticket.setGate(gate);
        ticket.setParkingAttendant(gate.getParkingAttendant());
        return this.ticketRepo.save(ticket);
    }
}
