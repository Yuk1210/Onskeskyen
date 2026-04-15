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

    public void opretOnske(int brugerId, String navn, String link, double pris, String billede) {
        onskeRepository.opretOnske(brugerId, navn, link, pris, billede);
    }
    public List<Onske> hentOnskerForListe(int onskelisteId) {
        return onskeRepository.hentOnskerForListe(onskelisteId);
    }

    public void opretOnskeTilListe(int onskelisteId, String navn, String link, double pris, String billede) {
        onskeRepository.opretOnskeTilListe(onskelisteId, navn, link, pris, billede);
    }

    public void deleteOnskeById(int onskeId) {
        onskeRepository.deleteOnskeById(onskeId);
    }
}
