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
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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

        if (salleSelectionnee == null) {
            System.out.println("Aucune salle sélectionnée !");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une salle à supprimer.");
            alert.showAndWait();
            return;
        }

        // Vérifier que l'ID est valide (non nul et > 0)
        int idASupprimer = salleSelectionnee.getNumero_salle();
        if (idASupprimer <= 0) {
            System.out.println("ID invalide: " + idASupprimer);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("ID invalide");
            alert.setContentText("Cette salle a un ID invalide (" + idASupprimer +
                    "). Elle n'existe probablement pas dans la base de données.\n" +
                    "Veuillez rafraîchir la liste des salles.");
            alert.showAndWait();

            // Rafraîchir la liste
            refreshSallesList();
            return;
        }

        // Confirmation avant suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Supprimer la salle");
        confirmation.setContentText("Voulez-vous vraiment supprimer la salle \"" +
                salleSelectionnee.getNom() + "\" (ID: " + idASupprimer + ") ?");

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean deleted = ModelQueries.deleteSalleApi(idASupprimer);

            if (deleted) {
                // Supprimer de la liste observable
                donnees_salles.remove(salleSelectionnee);
                System.out.println("✓ Salle supprimée avec succès !");

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("La salle a été supprimée avec succès.");
                successAlert.showAndWait();
            } else {
                System.err.println("✗ Erreur lors de la suppression");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Impossible de supprimer la salle (ID: " + idASupprimer + ").\n" +
                        "Elle n'existe peut-être pas dans la base de données.");
                errorAlert.showAndWait();

                // Rafraîchir la liste pour enlever les entrées invalides
                refreshSallesList();
            }
        }
    }

    // Méthode pour rafraîchir la liste des salles
    private void refreshSallesList() {
        List<Salle> salles = ModelQueries.getSallesFromApi();
        if (salles != null) {
            donnees_salles.clear();
            donnees_salles.addAll(salles);
            tableviewSalles.setItems(donnees_salles);
        }
    }

    // Méthode utilitaire pour afficher les alertes
    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
