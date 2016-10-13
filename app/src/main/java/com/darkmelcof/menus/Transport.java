package com.darkmelcof.menus;

/**
 * Created by Darkmelcof on 05/12/2015.
 */
public class Transport {

    private String nom;
    private String vitesse;

    // Construction
    public Transport(){}

    public Transport(String type, String vit){
        setNom(type);
        setVitesse(vit);
    }

    @Override
    public String toString(){
        return "Transport : " + getNom() + "\nVitesse : " + getVitesse();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVitesse() {
        return vitesse;
    }

    public void setVitesse(String vitesse) {
        this.vitesse = vitesse;
    }
}
