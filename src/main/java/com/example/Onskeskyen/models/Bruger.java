package com.example.Onskeskyen.models;

import java.time.LocalDateTime;

public class Bruger {
    private int brugerId;
    private String navn;
    private String email;
    private String kodeord;
    private LocalDateTime oprettetDato;

    public Bruger(int brugerId, String navn, String email, String kodeord, LocalDateTime oprettetDato){
        this.brugerId = brugerId;
        this.navn = navn;
        this.email = email;
        this.kodeord = kodeord;
        this.oprettetDato = oprettetDato;
    }

    public int getBrugerId() {
        return brugerId;
    }

    public void setBrugerId(int brugerId) {
        this.brugerId = brugerId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKodeord() {
        return kodeord;
    }

    public void setKodeord(String kodeord) {
        this.kodeord = kodeord;
    }

    public LocalDateTime getOprettetDato() {
        return oprettetDato;
    }

    // Rettet setter for dato
    public void setOprettetDato(LocalDateTime oprettetDato) {
        this.oprettetDato = oprettetDato;
    }

    @Override
    public String toString() {
        return "Bruger{" +
                "brugerId=" + brugerId +
                ", navn='" + navn + '\'' +
                ", email='" + email + '\'' +
                ", kodeord='" + kodeord + '\'' +
                ", dato=" + oprettetDato +
                '}';
    }
}