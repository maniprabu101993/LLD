package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.BookTicketRequestDTO;
import com.example.bmsbookticket.dtos.BookTicketResponseDTO;
import com.example.bmsbookticket.dtos.ResponseStatus;
import com.example.bmsbookticket.services.TicketService;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {
    private TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }


    public BookTicketResponseDTO bookTicket(BookTicketRequestDTO requestDTO) {
        BookTicketResponseDTO respionse=new BookTicketResponseDTO();
        try{
            respionse.setTicket(service.bookTicket(requestDTO.getShowSeatIds(),requestDTO.getUserId()));
            respionse.setStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            respionse.setStatus(ResponseStatus.FAILURE);
        }
        return respionse;
    }
}
