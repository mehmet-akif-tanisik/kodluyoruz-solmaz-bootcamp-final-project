package com.solmaz.ticketplannermainservice.repository;

import com.solmaz.ticketplannermainservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
