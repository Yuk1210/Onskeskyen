package com.example.Onskeskyen.services;

import com.example.Onskeskyen.models.Reservation;
import com.example.Onskeskyen.repositorys.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public void save(Reservation reservation) {
        repository.save(reservation);
    }

    public Reservation findByOnskeId(int onskeId) {
        return repository.findByOnskeId(onskeId);
    }

    public List<Reservation> findByBrugerId(int brugerId) {
        return repository.findByBrugerId(brugerId);
    }

    public boolean erReserveret(int onskeId) {
        return repository.erReserveret(onskeId);
    }

    public void deleteByOnskeId(int onskeId) {
        repository.deleteByOnskeId(onskeId);
    }

    public void deleteByOnskeIdAndBrugerId(int onskeId, int brugerId) {
        repository.deleteByOnskeIdAndBrugerId(onskeId, brugerId);
    }
}