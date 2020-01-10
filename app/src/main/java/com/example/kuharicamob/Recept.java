package com.example.kuharicamob;

import java.util.ArrayList;

public class Recept {
    private String nazivJela;
    private ArrayList<String> listaSastojci;
    private ArrayList<String> listaKoraci;

    public Recept() {

    }

    public String getNazivJela() {
        return nazivJela;
    }

    public void setNazivJela(String nazivJela) {
        this.nazivJela = nazivJela;
    }

    public ArrayList<String> getListaSastojci() {
        return listaSastojci;
    }

    public void setListaSastojci(ArrayList<String> listaSastojci) {
        this.listaSastojci = listaSastojci;
    }

    public ArrayList<String> getListaKoraci() {
        return listaKoraci;
    }

    public void setListaKoraci(ArrayList<String> listaKoraci) {
        this.listaKoraci = listaKoraci;
    }
}
