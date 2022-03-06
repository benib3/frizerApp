package com.example.frizer;

public class MyListData {
    private String ime,datum,vrijeme,usluga;


    public MyListData(String ime,String datum,String vrijeme,String usluga) {
        this.ime = ime;
        this.datum=datum;
        this.vrijeme=vrijeme;
        this.usluga=usluga;
    }


    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getUsluga() {
        return usluga;
    }

    public void setUsluga(String usluga) {
        this.usluga = usluga;
    }
}
