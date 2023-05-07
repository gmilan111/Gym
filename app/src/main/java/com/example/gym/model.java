package com.example.gym;

public class model
{
  String id, gyakorlatNev, ismetlesSzam, maxSuly, sorozatSzam;

    public model() {
    }

    public model(String id, String gyakorlatNev, String ismetlesSzam, String maxSuly, String sorozatSzam) {
        this.id = id;
        this.gyakorlatNev = gyakorlatNev;
        this.ismetlesSzam = ismetlesSzam;
        this.maxSuly = maxSuly;
        this.sorozatSzam = sorozatSzam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGyakorlatNev() {
        return gyakorlatNev;
    }

    public void setGyakorlatNev(String gyakorlatNev) {
        this.gyakorlatNev = gyakorlatNev;
    }

    public String getIsmetlesSzam() {
        return ismetlesSzam;
    }

    public void setIsmetlesSzam(String ismetlesSzam) {
        this.ismetlesSzam = ismetlesSzam;
    }

    public String getMaxSuly() {
        return maxSuly;
    }

    public void setMaxSuly(String maxSuly) {
        this.maxSuly = maxSuly;
    }

    public String getSorozatSzam() {
        return sorozatSzam;
    }

    public void setSorozatSzam(String sorozatSzam) {
        this.sorozatSzam = sorozatSzam;
    }
}
