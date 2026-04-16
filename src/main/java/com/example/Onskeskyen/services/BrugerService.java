package com.example.Onskeskyen.services;

import com.example.Onskeskyen.models.Bruger;
import com.example.Onskeskyen.repositorys.BrugerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrugerService {

    private final BrugerRepository brugerRepository;

    public BrugerService(BrugerRepository brugerRepository) {
        this.brugerRepository = brugerRepository;
    }

    public List<Bruger> getAllBrugere() {
        return brugerRepository.findAll();
    }

    public Bruger findBruger(int brugerId) {
        return brugerRepository.findById(brugerId).orElse(null);
    }

    public void opretBruger(String navn, String email, String kodeord) {
        Bruger bruger = new Bruger(0, navn, email, kodeord, LocalDateTime.now());
        brugerRepository.save(bruger);
    }

    public void updateBrugerById(int id, String navn, String email, String kodeord) {
        brugerRepository.updateById(id, navn, email, kodeord);
    }

    public void deleteBrugerById(int id) {
        brugerRepository.deleteById(id);
    }

    public boolean godkendLogin(String email, String kodeord) {
        email = email.trim();
        kodeord = kodeord.trim();

        Bruger bruger = findBrugerByEmail(email);

        if (bruger == null) {
            return false;
        }

        return bruger.getKodeord().equals(kodeord);
    }

    public Bruger findBrugerByEmail(String email) {
        return brugerRepository.findByEmail(email).orElse(null);
    }

    public Bruger findByEmailAndKodeord(String email, String kodeord) {
        return brugerRepository.findByEmailAndKodeord(email, kodeord).orElse(null);
    }
}