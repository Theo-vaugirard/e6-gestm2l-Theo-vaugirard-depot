package m2l.desktop.gestion.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author nathalie
 */
public class Salle {

    private int numero_salle;
    private String nom;
    private int capacite;
    private String equipements;
    private String services;
    public String batiment;

    public Salle(int numero_salle, String nom, int capacite, String equipements, String services, String batiment) {
        this.nom = nom;
        this.capacite = capacite;
        this.equipements = equipements;
        this.services = services;
        this.batiment = batiment;
        this.numero_salle = numero_salle;
    }

    public Salle(int num, String nom, String batiment) {
        this.batiment = batiment;
        this.numero_salle = num;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getEquipements() {
        return equipements;
    }

    public void setEquipements(String equipements) {
        this.equipements = equipements;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public int getNumero_salle() {
        return numero_salle;
    }

    public void setNumero_salle(int numero_salle) {
        this.numero_salle = numero_salle;
    }


    @Override
    public String toString() {
        return "Salle{" +
                "nom='" + nom + '\'' +
                ", capacite=" + capacite +
                ", equipements='" + equipements + '\'' +
                ", services='" + services + '\'' +
                ", batiment='" + batiment + '\'' +
                '}';
    }


    //Les propriétés pour la TableView
    public SimpleStringProperty nomProperty() {
        return new SimpleStringProperty(nom);
    }

    public SimpleStringProperty batimentProperty() {
        return new SimpleStringProperty(batiment);
    }

    public SimpleStringProperty servicesProperty() {
        return new SimpleStringProperty(services);
    }

    public SimpleStringProperty equipementsProperty() {
        return new SimpleStringProperty(equipements);
    }

 public SimpleIntegerProperty capaciteProperty() {
        return new SimpleIntegerProperty(capacite);
    }


    public SimpleIntegerProperty    numero_salleProperty() {
        return new SimpleIntegerProperty(numero_salle);
    }


}
