package m2l.desktop.gestion.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Climatiseur {

    private int id;
    private String modele;
    private String marque;
    private int  puissance;
    private int surface_min;
    private int  surface_max;

    private Salle salle;


    public Climatiseur(Salle salle, int surface_max, int surface_min, int puissance, String marque, String modele, int id) {
        this.salle = salle;
        this.surface_max = surface_max;
        this.surface_min = surface_min;
        this.puissance = puissance;
        this.marque = marque;
        this.modele = modele;
        this.id = id;
    }

    public Climatiseur(String marque, String modele, int puissance) {
        this.puissance =puissance;
        this.marque = marque;
        this.modele = modele;


    }

    public Climatiseur(int i,String mar, String mod, int pui, int smi, int sma) {
        this.setMarque( mar);
        this.setModele( mod);
        this.setPuissance(pui);
        this.setSurface_max(sma);
        this.setSurface_min(smi);
        this.id = i;
    }

    public int getSurfaceMaxCouverte() {
        switch (this.puissance) {
            case 7000:
                return 15;
            case 9000:
                return 25;
            case 12000:
                return 35;
            case 18000:
                return 50;
            case 24000:
                return 70;
            case 30000:
                return 80;
        }
        return 0;
    }

    public int getSurfaceMinCouverte() {
        switch (this.puissance) {
            case 7000:
                return 7;
            case 9000:
                return 15;
            case 12000:
                return 25;
            case 18000:
                return 35;
            case 24000:
                return 50;
            case 30000:
                return 70;
        }
        return 0;
    }

    public List<Integer> getSurfaceCouverte() {


        List<Integer> surfaces = new ArrayList<>(2);

        switch (this.puissance) {
            case 7000:
                surfaces.add(7);
                surfaces.add(15);
                break;
            case 9000:
                surfaces.add(15);
                surfaces.add(25);
                break;
            case 12000:
                surfaces.add(25);
                surfaces.add(35);
                break;
            case 18000:
                surfaces.add(35);
                surfaces.add(50);
                break;
            case 24000:
                surfaces.add(50);
                surfaces.add(70);
                break;
            case 30000:
                surfaces.add(70);
                surfaces.add(80);
                break;
            default:
                surfaces.add(7);
                surfaces.add(15);
                break;
        }
        return surfaces;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public int getSurface_min() {
        return surface_min;
    }

    public void setSurface_min(int surface_min) {
        this.surface_min = surface_min;
    }

    public int getSurface_max() {
        return surface_max;
    }

    public void setSurface_max(int surface_max) {
        this.surface_max = surface_max;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    //Les propriétés pour la TableView
    public SimpleIntegerProperty puissanceProperty() {
        return new SimpleIntegerProperty(puissance);
    }

    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleIntegerProperty surface_minProperty() {
        return new SimpleIntegerProperty(surface_min);
    }

    public SimpleIntegerProperty surface_maxProperty() {
        return new SimpleIntegerProperty(surface_max);
    }

    public SimpleStringProperty marqueProperty() {
        return new SimpleStringProperty(marque);
    }

    public SimpleStringProperty modeleProperty() {
        return new SimpleStringProperty(modele);
    }

    @Override
    public String toString() {
        return "Climatiseur{" +
                "id=" + id +
                ", modele='" + modele + '\'' +
                ", marque='" + marque + '\'' +
                ", puissance=" + puissance +
                ", surface_min=" + surface_min +
                ", surface_max=" + surface_max +
                ", salle=" + salle.toString() +
                '}';
    }
}