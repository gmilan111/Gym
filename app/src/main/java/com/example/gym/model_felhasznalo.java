package com.example.gym;

public class model_felhasznalo {
    String felhasznalonev, email, jelszo;

    public model_felhasznalo() {
    }

    public model_felhasznalo(String felhasznalonev, String email, String jelszo) {
        this.felhasznalonev = felhasznalonev;
        this.email = email;
        this.jelszo = jelszo;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public void setFelhasznalonev(String felhasznalonev) {
        this.felhasznalonev = felhasznalonev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJelszo() {
        return jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }
}
