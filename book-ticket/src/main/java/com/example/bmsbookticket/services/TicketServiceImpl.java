package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TicketServiceImpl implements TicketService {
    private SeatsRepository seatsRepository;
    private ShowRepository showRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private ShowSeatRepository showSeatRepository;

    public TicketServiceImpl(SeatsRepository seatsRepository,
                             ShowRepository showRepository,
                             TicketRepository ticketRepository,
                             UserRepository userRepository,
                             ShowSeatRepository showSeatRepository) {
        this.seatsRepository = seatsRepository;
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws Exception {
        User user= userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        List<ShowSeat> list = new ArrayList<>();
        List<Seat> seats = new ArrayList<>();
        for (Integer seatId : showSeatIds) {
            ShowSeat showSeat = showSeatRepository.findById(seatId).orElseThrow(() -> new Exception("Seat not found"));
            if (showSeat.getStatus() != SeatStatus.AVAILABLE) {
                throw new Exception("Seat already booked");
            }
            showSeat.setStatus(SeatStatus.BLOCKED);
            showSeatRepository.save(showSeat);
            list.add(showSeat);
            seats.add(showSeat.getSeat());
        }
        Ticket ticket = getTicket(list, user, seats);
        return ticketRepository.save(ticket);
    }

    private static Ticket getTicket(List<ShowSeat> list, User user, List<Seat> seats){
        Show show = list.get(0).getShow();
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setTimeOfBooking(new Date());
        ticket.setSeats(seats);
        ticket.setStatus(TicketStatus.UNPAID);
        return ticket;
    }
}
