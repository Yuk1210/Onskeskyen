package com.example.Onskeskyen.models;

public class Onske {
    private int onskeId;
    private int onskelisteId;
    private String navn;
    private String beskrivelse;
    private String produktLink;
    private double pris;
    private String billedeLink;
    private boolean kobt;

    public Onske() {
    }

    public Onske(int onskeId, int onskelisteId, String navn, String beskrivelse, String produktLink, double pris, String billedeLink, boolean kobt) {
        this.onskeId = onskeId;
        this.onskelisteId = onskelisteId;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.produktLink = produktLink;
        this.pris = pris;
        this.billedeLink = billedeLink;
        this.kobt = kobt;
    }

    public int getOnskeId() {
        return onskeId;
    }

    public void setOnskeId(int onskeId) {
        this.onskeId = onskeId;
    }

    public int getOnskelisteId() {
        return onskelisteId;
    }

    public void setOnskelisteId(int onskelisteId) {
        this.onskelisteId = onskelisteId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getProduktLink() {
        return produktLink;
    }

    public void setProduktLink(String produktLink) {
        this.produktLink = produktLink;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public String getBilledeLink() {
        return billedeLink;
    }

    public void setBilledeLink(String billedeLink) {
        this.billedeLink = billedeLink;
    }

    public boolean isKobt() {
        return kobt;
    }

    public void setKobt(boolean kobt) {
        this.kobt = kobt;
    }
}