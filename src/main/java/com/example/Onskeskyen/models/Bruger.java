package com.example.Onskeskyen.models;

import java.time.LocalDateTime;

public class Bruger {
    private int brugerId;
    private String navn;
    private String email;
    private String kodeord;
    private LocalDateTime dato;


    public Bruger(int brugerId, String navn, String email, String kodeord, LocalDateTime dato){
        this.brugerId=brugerId;
        this.navn = navn;
        this.email = email;
        this.kodeord= kodeord;
        this.dato = dato;
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

    public LocalDateTime getDato() {
        return dato;
    }

    public void sDato(LocalDateTime oprettetDato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        return "Bruger" +
                "brugerId" + brugerId +
                ", navn" + navn + '\'' +
                ", email" + email +
                ", kodeord " + kodeord +
                ", oprettetDato " + dato;
    }
}
