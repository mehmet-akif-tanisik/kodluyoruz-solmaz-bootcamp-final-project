package com.solmaz.ticketplannermainservice.taskmanagement;

import com.solmaz.ticketplannermainservice.model.enums.PaymentStatus;
import com.solmaz.ticketplannermainservice.repository.BookingRepository;
import com.solmaz.ticketplannermainservice.repository.TicketRepository;
import com.solmaz.ticketplannermainservice.repository.VoyageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class BookingTaskManagement {
    private final BookingRepository bookingRepository;
    private final VoyageRepository voyageRepository;
    private final TicketRepository ticketRepository;

    public BookingTaskManagement(BookingRepository bookingRepository,
                                 VoyageRepository voyageRepository, TicketRepository ticketRepository) {
        this.bookingRepository = bookingRepository;
        this.voyageRepository = voyageRepository;
        this.ticketRepository = ticketRepository;
    }

    @PostConstruct
    public void checkPackageExpiration() {
        var timer = new Timer();
        long period = 10 * 60 * 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("Booking cleaner thread is working... " + LocalDateTime.now());

                var list = bookingRepository.findAll().stream()
                        .filter(u -> u.getCreationDateTime() != null)
                        .filter(u -> LocalDateTime.now().isAfter(u.getCreationDateTime().plusMinutes(10)))
                        .toList()
                        .stream()
                        .filter(booking -> booking.getPaymentStatus().equals(PaymentStatus.PENDING))
                        .toList();

                list.forEach(booking -> {

                    booking.setActive(false);
                    booking.getTicketList().forEach(ticket -> {
                        ticket.getVoyage().setAvailableSeat(ticket.getVoyage().getAvailableSeat() + 1);
                        voyageRepository.save(ticket.getVoyage());
                        ticket.setVoyage(null);
                        ticket.setBooking(null);
                        ticketRepository.delete(ticket);
                    });
                    bookingRepository.delete(booking);
                });
            }
        }, 1000, period);
    }

}
