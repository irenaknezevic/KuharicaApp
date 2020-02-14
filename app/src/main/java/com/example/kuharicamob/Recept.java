package com.example.kuharicamob;

import java.util.ArrayList;

public class Recept {
    private String  nId;
    private String nazivJela;
    private ArrayList<String> listaSastojci;
    private ArrayList<String> listaKoraci;
    private String userID;
    private Float ocjena;
    private int brojOcjena;


    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
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


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public float getOcjena() {
        if(ocjena != null)
        {
            return ocjena;
        }
        return 0;
    }

    public void setOcjena(float ocjena) {
        this.ocjena = ocjena;
    }

    public int getBrojOcjena() {
        return brojOcjena;
    }

    public void setBrojOcjena(int brojOcjena) {
        this.brojOcjena = brojOcjena;
    }
}
