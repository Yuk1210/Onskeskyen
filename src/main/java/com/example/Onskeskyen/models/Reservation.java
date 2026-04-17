package com.example.Onskeskyen.models;

import java.time.LocalDateTime;

public class Reservation {

    private int reservationId;
    private int onskeId;
    private int brugerId;
    private LocalDateTime dato;

    public Reservation() {
    }

    public Reservation(int reservationId, int onskeId, int brugerId, LocalDateTime dato) {
        this.reservationId = reservationId;
        this.onskeId = onskeId;
        this.brugerId = brugerId;
        this.dato = dato;
    }

    public Reservation(int onskeId, int brugerId) {
        this.onskeId = onskeId;
        this.brugerId = brugerId;
        this.dato = LocalDateTime.now();
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getOnskeId() {
        return onskeId;
    }

    public void setOnskeId(int onskeId) {
        this.onskeId = onskeId;
    }

    public int getBrugerId() {
        return brugerId;
    }

    public void setBrugerId(int brugerId) {
        this.brugerId = brugerId;
    }

    public LocalDateTime getDato() {
        return dato;
    }

    public void setDato(LocalDateTime dato) {
        this.dato = dato;
    }
}