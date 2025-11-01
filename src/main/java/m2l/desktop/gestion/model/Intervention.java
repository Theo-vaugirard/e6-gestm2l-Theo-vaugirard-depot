package m2l.desktop.gestion.model;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author nathalie
 */
public class Intervention {
    private int id_intervention;
    private int num_salle;
    private int num_intervenant;
    private LocalDateTime date_heure ;
    private String motif;

    private Intervenant intervenant;

    private Salle salle;

    public Intervention(int id_intervention, String motif, LocalDateTime  date ,int num_intervenant, int num_salle, Salle salle, Intervenant intervenant) {
        this.id_intervention = id_intervention;
        this.motif = motif;
        this.date_heure  = date ;
        this.num_intervenant = num_intervenant;
        this.num_salle = num_salle;
        this.salle = salle;
        this.intervenant = intervenant;

    }

    public Intervention(String motif) {
        this.motif = motif;
    }

    public int getId_intervention() {
        return id_intervention;
    }

    public void setId_intervention(int id_intervention) {
        this.id_intervention = id_intervention;
    }

    public int getnum_salle() {
        return num_salle;
    }

    public void setnum_salle(int num_salle) {
        this.num_salle = num_salle;
    }

    public int getnum_intervenant() {
        return num_intervenant;
    }

    public void setnum_intervenant(int num_intervenant) {
        this.num_intervenant = num_intervenant;
    }

    public LocalDateTime getDate_heure() {
        return date_heure;
    }

    public void setDate_heure(LocalDateTime date_heure) {
        this.date_heure = date_heure;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public String toString() {
        return "Intervention n°" + this.id_intervention + " le " + this.date_heure  + " pour le motif : " + this.motif +
                "\n-- " + this.salle.toString() + "\n-- " + this.intervenant.toString();
    }


    //Les propriétés pour la TableView
    public SimpleStringProperty getDateProperty() {

        return new SimpleStringProperty(date_heure .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

    public SimpleStringProperty getMotifProperty() {

        return new SimpleStringProperty(this.motif);

    }

    public SimpleStringProperty motifProperty() {
        return new SimpleStringProperty(motif);
    }
}
