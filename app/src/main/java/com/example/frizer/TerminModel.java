package com.example.frizer;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class TerminModel {
    private String terminID,datumTermina,vrijemeTermina,ime,prezime,email,usluga;

    public String getTerminID() {
        return terminID;
    }

    public void setTerminID(String terminID) {
        this.terminID = terminID;
    }

    public String getDatumTermina() {
        return datumTermina;
    }

    public void setDatumTermina(String datumTermina) {
        this.datumTermina = datumTermina;
    }

    public String getVrijemeTermina() {
        return vrijemeTermina;
    }

    public void setVrijemeTermina(String vrijemeTermina) {
        this.vrijemeTermina = vrijemeTermina;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsluga() {
        return usluga;
    }

    public void setUsluga(String usluga) {
        this.usluga = usluga;
    }

    public String srediDatum(){
        LocalDate datumFormatiran = LocalDate.parse(this.getDatumTermina().substring(0,this.getDatumTermina().indexOf("T")));
        return datumFormatiran.getDayOfMonth()+"."+datumFormatiran.getMonthValue()+"."+datumFormatiran.getYear()+".";
    }

    @NonNull
    @Override
    public String toString() {
        return "Klijent : "+this.getIme()+" "+this.getPrezime()+
                "\nEmail : "+this.getEmail()+
                "\nDatum : "+this.srediDatum()
                +"\nVrijeme : "+this.getVrijemeTermina()
                +"\nUsluga : "+this.getUsluga();
    }
}
