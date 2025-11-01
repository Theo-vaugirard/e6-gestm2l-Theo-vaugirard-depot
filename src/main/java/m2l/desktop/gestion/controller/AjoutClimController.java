package m2l.desktop.gestion.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import m2l.desktop.gestion.model.Climatiseur;
import m2l.desktop.gestion.model.ModelQueries;
import m2l.desktop.gestion.model.Tools;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjoutClimController implements Initializable {

    @FXML
    private Label label, erreur_marque, erreur_modele, erreur_puissance;

    @FXML
    private TextArea valpuiss, valmarque, valmodele, valconso, valson, valclasse;


    @FXML
    private void enregistrer_nouveau_climatiseur(ActionEvent event) throws IOException {

        System.out.println("Enregistrement du climatiseur .... ");

        //création d'un nouvel objet
        Climatiseur c = new Climatiseur("marque", "model", 12000);
        //Salle par défaut
        c.setSalle(ModelQueries.getSalleById(14));
        //Ajout du climatiseur dans la liste des climatiseurs affichée
        ClimsTableviewController.getListeClimatiseurs().add(c);

        ModelQueries.insertClimatiseurViaApi(c);

        fermerFenetre(event);

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void fermerFenetre(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}