package com.darkmelcof.menus;

/**
 * Created by Darkmelcof on 05/12/2015.
 */
public class Destination {

    private String nom;
    private String latitude;
    private String longitude;

    // Construction
    public Destination(){}

    public Destination(String nom, String lat, String lon){
        this.setNom(nom);
        this.setLatitude(lat);
        this.setLongitude(lon);
    }
    public String toString(){
        return "nom : " + getNom() + "\nlatitude : " + getLatitude() + "\nlongitude : " + getLongitude();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
