package com.solmaz.ticketplannermainservice.repository;

import com.solmaz.ticketplannermainservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
