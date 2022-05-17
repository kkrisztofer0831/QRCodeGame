package com.example.qrgame;

import java.util.Spliterators;

public class csapat {
    private String csapatnev;
    private String Allomas;
    private Integer Pontszam;

    public csapat(String csapatnev, String Allomas, Integer Pontszam){
        this.csapatnev = csapatnev;
        this.Allomas = Allomas;
        this.Pontszam = Pontszam;
    }


    public String getAllomas() {
        return Allomas;
    }

    public Integer getPontszam() {
        return Pontszam;
    }

    public String getCsapatnev() {
        return csapatnev;
    }

    public void setCsapatnev(String csapatnev) {
        this.csapatnev = csapatnev;
    }
}
