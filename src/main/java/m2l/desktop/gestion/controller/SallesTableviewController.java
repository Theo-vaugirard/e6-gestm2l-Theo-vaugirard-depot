package m2l.desktop.gestion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import m2l.desktop.gestion.model.ModelQueries;
import m2l.desktop.gestion.model.Salle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

/*
⚙️ 3. CONTROLLER (SallesTableviewController.java)
-----------------------------------------------------------
- Récupère les données depuis l’API via ModelQueries
- Associe chaque colonne à une propriété de la classe Salle :
  • nomProperty()
  • capaciteProperty()
  • equipementsProperty()
  • batimentProperty()
- Centre les colonnes Capacité et Bâtiment
- Transforme la liste en ObservableList
- Injecte les données dans le TableView
 */

public class SallesTableviewController implements Initializable {

    @FXML
    public TableView<Salle> tableviewSalles;

    @FXML
    public TableColumn<Salle, String> nomCol;

    @FXML
    public TableColumn<Salle, Number> capaciteCol;

    @FXML
    public TableColumn<Salle, String> equipementsCol;

    @FXML
    public TableColumn<Salle, String> batimentCol;

    @FXML
    public void ajouterSalle() {

        Stage popup = new Stage();
        popup.setTitle("Ajouter une salle");

        VBox layout = new VBox(10);

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");

        TextField capaciteField = new TextField();
        capaciteField.setPromptText("Capacité");

        TextField equipementsField = new TextField();
        equipementsField.setPromptText("Équipements");

        TextField batimentField = new TextField();
        batimentField.setPromptText("Bâtiment");

        Button btnValider = new Button("Ajouter");

        btnValider.setOnAction(e -> {

            // sécurisation capacité
            int capacite = 0;
            try {
                capacite = Integer.parseInt(capaciteField.getText());
            } catch (Exception ex) {
                System.out.println("Capacité invalide");
            }

            // création objet
            Salle nouvelleSalle = new Salle(
                    0,
                    nomField.getText(),
                    capacite,
                    equipementsField.getText(),
                    null,
                    batimentField.getText()
            );

            // appel API
            Salle salleAjoutee = ModelQueries.ajouterSalleApi(nouvelleSalle);

            // mise à jour table
            if (salleAjoutee != null) {
                donnees_salles.add(salleAjoutee);
                popup.close();
            } else {
                System.out.println("Erreur lors de l'ajout en base");
            }
        });

        layout.getChildren().addAll(
                nomField,
                capaciteField,
                equipementsField,
                batimentField,
                btnValider
        );

        Scene scene = new Scene(layout, 300, 250);
        popup.setScene(scene);
        popup.show();
    }

    @FXML
    public void supprimerSalle(MouseEvent event) {

        Salle salleSelectionnee = tableviewSalles.getSelectionModel().getSelectedItem();

        if (salleSelectionnee != null) {
            donnees_salles.remove(salleSelectionnee);
        } else {
            System.out.println("Aucune salle sélectionnée !");
        }
    }

    private List<Salle> liste_des_salles = new ArrayList<>();
    private ObservableList<Salle> donnees_salles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurerOngletSalles();
    }

    private void configurerOngletSalles() {

        System.out.println("Chargement des salles...");

        liste_des_salles = ModelQueries.getSallesFromApi();

        // liaison colonnes
        nomCol.setCellValueFactory(cell -> cell.getValue().nomProperty());
        capaciteCol.setCellValueFactory(cell -> cell.getValue().capaciteProperty());
        equipementsCol.setCellValueFactory(cell -> cell.getValue().equipementsProperty());
        batimentCol.setCellValueFactory(cell -> cell.getValue().batimentProperty());

        // centrage
        capaciteCol.setStyle("-fx-alignment: CENTER;");
        batimentCol.setStyle("-fx-alignment: CENTER;");

        // données
        donnees_salles = FXCollections.observableList(liste_des_salles);
        tableviewSalles.setItems(donnees_salles);
    }
}
