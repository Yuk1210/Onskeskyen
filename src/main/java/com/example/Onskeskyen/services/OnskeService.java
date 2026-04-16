package com.example.Onskeskyen.services;

import com.example.Onskeskyen.models.Onske;
import com.example.Onskeskyen.repositorys.OnskeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnskeService {

    private final OnskeRepository onskeRepository;

    public OnskeService(OnskeRepository onskeRepository) {
        this.onskeRepository = onskeRepository;
    }

    public List<Onske> hentOnsker(int brugerId) {
        return onskeRepository.hentOnsker(brugerId);
    }

    public List<Onske> hentOnskerForListe(int onskelisteId) {
        return onskeRepository.hentOnskerForListe(onskelisteId);
    }

    public Onske findById(int onskeId) {
        return onskeRepository.findById(onskeId);
    }

    public void opretOnske(int brugerId, String navn, String beskrivelse, String link, double pris, String billede) {
        onskeRepository.opretOnske(brugerId, navn, beskrivelse, link, pris, billede);
    }

    public void opretOnskeTilListe(int onskelisteId, String navn, String beskrivelse, String link, double pris, String billede) {
        onskeRepository.opretOnskeTilListe(onskelisteId, navn, beskrivelse, link, pris, billede);
    }

    public void markAsKobt(int onskeId) {
        onskeRepository.markAsKobt(onskeId);
    }

    public void markAsNotKobt(int onskeId) {
        onskeRepository.markAsNotKobt(onskeId);
    }

    public boolean erKobt(int onskeId) {
        return onskeRepository.erKobt(onskeId);
    }

    public void deleteOnskeById(int onskeId) {
        onskeRepository.deleteOnskeById(onskeId);
    }
}