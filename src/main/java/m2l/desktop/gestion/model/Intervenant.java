/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2l.desktop.gestion.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author nathalie
 */
public class Intervenant
{
    private int numero_intervenant;
    private String nom, prenom;
    private int telephone;
    private String societe_siret;

    public Intervenant(int numero_intervenant, String nom, String prenom, int telephone, String societe_siret) {
        this.numero_intervenant = numero_intervenant;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.societe_siret = societe_siret;
    }

    public int getNumero_intervenant() {
        return numero_intervenant;
    }

    public void setNumero_intervenant(int numero_intervenant) {
        this.numero_intervenant = numero_intervenant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSociete_siret() {
        return societe_siret;
    }

    public void setSociete_siret(String societe_siret) {
        this.societe_siret = societe_siret;
    }

    @Override
    public String toString() {
        return "Intervenant{" +
                "numero_intervenant=" + numero_intervenant +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone=" + telephone +
                ", societe_siret=" + societe_siret +
                '}';
    }


    //Les propriétés pour la TableView
    public SimpleIntegerProperty numeroIntervenantProperty() {
        return new SimpleIntegerProperty(numero_intervenant);
    }
    public SimpleStringProperty nomProperty() {
        return new SimpleStringProperty(nom);
    }
    public SimpleStringProperty prenomProperty() {
        return new SimpleStringProperty(prenom);
    }

    public SimpleIntegerProperty telephoneProperty() {
        return new SimpleIntegerProperty(telephone);
    }

    public SimpleStringProperty societeSiretProperty() {
        return new SimpleStringProperty(societe_siret);
    }
}
