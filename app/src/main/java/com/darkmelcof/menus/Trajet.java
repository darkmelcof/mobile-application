package com.darkmelcof.menus;

/**
 * Created by Darkmelcof on 06/12/2015.
 */
public class Trajet {

    private Destination depart;
    private Destination arrivee;
    private Double distance = 0.0;
    private long duree = 0;

    /**
     * Constructeur
     */
    public Trajet(){

    }

    public Trajet(Destination dpt, Destination arv, Transport tpt){
        setDepart(dpt);
        setArrivee(arv);
        setDistance(calculTrajet(dpt, arv));
        setDuree(calculTempTrajet(getDistance(), tpt));
    }

    /**
     * Calcul de la diagonale entre les coordonnees
     */
    private Double calculTrajet(Destination dpt, Destination arv){
        return 32.5*Math.sqrt(Math.pow(Double.valueOf(dpt.getLatitude()) - Double.valueOf(arv.getLatitude()),2)
                + Math.pow(Double.valueOf(dpt.getLongitude()) - Double.valueOf(arv.getLongitude()), 2));
    }

    /**
     * Calcul du temps de trajet en s
     *
     */
    private long calculTempTrajet(Double dist, Transport tpt){
        return Math.round(dist / Double.valueOf(tpt.getVitesse()));
    }

    public String toString(){
        return "depart : " + getDepart().getNom() + "\narrivee : " + getArrivee().getNom() + "\ndistance : "+ getDistance() + "\nduree : " + getDuree();
    }

    public Destination getDepart() {
        return depart;
    }

    public void setDepart(Destination depart) {
        this.depart = depart;
    }

    public Destination getArrivee() {
        return arrivee;
    }

    public void setArrivee(Destination arrivee) {
        this.arrivee = arrivee;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }
}
