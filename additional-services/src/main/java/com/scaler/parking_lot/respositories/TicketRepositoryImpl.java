package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Ticket;

import java.util.*;

public class TicketRepositoryImpl implements TicketRepository{
    private Map<Long, Ticket> ticketMap;
    private static int id = 0;

    public TicketRepositoryImpl() {
        this.ticketMap = new HashMap<>();
    }

    public Ticket save(Ticket ticket) {
        if(ticket.getId() == 0){
            ticket.setId(id++);
        }
        this.ticketMap.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> getTicketById(long ticketId) {
        return Optional.ofNullable(this.ticketMap.get(ticketId));
    }
}
