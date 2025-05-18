package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TicketService {
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws Exception;

}
