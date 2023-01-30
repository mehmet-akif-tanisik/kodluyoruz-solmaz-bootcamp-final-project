package com.solmaz.ticketplannermainservice.taskmanagement;

import com.solmaz.ticketplannermainservice.model.enums.VoyageStatus;
import com.solmaz.ticketplannermainservice.repository.VoyageRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class VoyageTaskManagement {

    private final VoyageRepository voyageRepository;

    public VoyageTaskManagement(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    @PostConstruct
    public void checkVoyageDateTime() {
        var timer = new Timer();
        long period = 1 * 60 * 60 * 1000;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("Voyage task manager thread is working... " + LocalDateTime.now());
                var list = voyageRepository
                        .findAll().stream()
                        .filter(voyage -> voyage.getVoyageStatus()
                                .equals(VoyageStatus.ACTIVE) && voyage.getVoyageDateTime().isBefore(LocalDateTime.now()))
                        .toList();
                list.forEach(voyage -> voyage.setVoyageStatus(VoyageStatus.PASSIVE));
                voyageRepository.saveAll(list);

            }
        }, 1000, period);
    }
}
