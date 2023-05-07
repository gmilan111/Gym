package com.example.gym;

public class model_food {
    String id, etelNev, mennyiseg, kaloria;

    public model_food() {
    }

    public model_food(String id, String etelNev, String mennyiseg, String kaloria) {
        this.id = id;
        this.etelNev = etelNev;
        this.mennyiseg = mennyiseg;
        this.kaloria = kaloria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtelNev() {
        return etelNev;
    }

    public void setEtelNev(String etelNev) {
        this.etelNev = etelNev;
    }

    public String getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(String mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getKaloria() {
        return kaloria;
    }

    public void setKaloria(String kaloria) {
        this.kaloria = kaloria;
    }
}
