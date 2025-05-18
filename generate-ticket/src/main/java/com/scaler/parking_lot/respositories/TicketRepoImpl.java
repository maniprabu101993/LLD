package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Ticket;

import java.util.*;

public class TicketRepoImpl implements TicketRepository {
    private final List<Ticket> ticketList = new ArrayList<>();
    @Override
    public Ticket save(Ticket ticket) {
        ticketList.add(ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> getTicketById(long ticketId) {
        for(Ticket ticket:ticketList){
            if(ticket.getId()==ticketId){
                return Optional.of(ticket);
            }
        }
        return Optional.empty();
    }
}
