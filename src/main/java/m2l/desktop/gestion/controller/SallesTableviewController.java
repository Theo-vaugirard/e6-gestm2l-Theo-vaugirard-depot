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
import java.util.stream.Collectors;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.geometry.Insets;

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
    public TableColumn<Salle, String> servicesCol;

    @FXML
    public TableColumn<Salle, String> batimentCol;

    @FXML
    public void ajouterSalle() {

        Stage popup = new Stage();
        popup.setTitle("Ajouter une salle");

        // Ajouter une icône à la fenêtre (utiliser une icône existante, à remplacer par celle fournie)
        try {
            popup.getIcons().add(new Image("m2l/desktop/gestion/images/icon1.png"));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'icône : " + e.getMessage());
        }

        // Enlever la barre Windows, garder juste la croix
        popup.initStyle(javafx.stage.StageStyle.UTILITY);

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Champ Nom
        javafx.scene.control.Label labelNom = new javafx.scene.control.Label("Nom");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");

        // Champ Capacité
        javafx.scene.control.Label labelCapacite = new javafx.scene.control.Label("Capacité");
        TextField capaciteField = new TextField();
        capaciteField.setPromptText("Capacité");

        // Champ Équipements
        javafx.scene.control.Label labelEquipements = new javafx.scene.control.Label("Équipements");
        TextField equipementsField = new TextField();
        equipementsField.setPromptText("Équipements");

        // Champ Services
        javafx.scene.control.Label labelServices = new javafx.scene.control.Label("Services");
        TextField servicesField = new TextField();
        servicesField.setPromptText("Services");

        // Champ Bâtiment
        javafx.scene.control.Label labelBatiment = new javafx.scene.control.Label("Bâtiment");
        ComboBox<String> batimentCombo = new ComboBox<>();
        batimentCombo.setPromptText("Sélectionner un bâtiment");

        // Récupérer les bâtiments existants depuis les salles
        List<Salle> sallesExistantes = ModelQueries.getSallesFromApi();
        if (sallesExistantes != null) {
            List<String> batiments = sallesExistantes.stream()
                    .map(Salle::getBatiment)
                    .distinct()
                    .collect(Collectors.toList());
            batimentCombo.setItems(FXCollections.observableArrayList(batiments));
        }

        Button btnValider = new Button("Ajouter");
        Button btnAnnuler = new Button("Annuler");

        // Style du bouton Ajouter en vert
        btnValider.setStyle("-fx-font-size: 12; -fx-padding: 8 20; -fx-background-color: #4CAF50; -fx-text-fill: white;");

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
                    servicesField.getText(),
                    batimentCombo.getValue() != null ? batimentCombo.getValue() : ""
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

        btnAnnuler.setOnAction(e -> popup.close());

        // Ajouter les labels et champs au layout principal
        mainLayout.getChildren().addAll(
                labelNom,
                nomField,
                labelCapacite,
                capaciteField,
                labelEquipements,
                equipementsField,
                labelServices,
                servicesField,
                labelBatiment,
                batimentCombo
        );

        // Créer un HBox pour les boutons alignés à droite
        javafx.scene.layout.HBox buttonLayout = new javafx.scene.layout.HBox(10);
        buttonLayout.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        buttonLayout.getChildren().addAll(btnAnnuler, btnValider);

        // Créer un BorderPane pour positionner les boutons en bas
        javafx.scene.layout.BorderPane borderPane = new javafx.scene.layout.BorderPane();
        borderPane.setCenter(mainLayout);
        borderPane.setBottom(buttonLayout);
        borderPane.setPadding(new Insets(10));

        Scene scene = new Scene(borderPane, 400, 450);
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
        servicesCol.setCellValueFactory(cell -> cell.getValue().servicesProperty());
        batimentCol.setCellValueFactory(cell -> cell.getValue().batimentProperty());

        // centrage
        capaciteCol.setStyle("-fx-alignment: CENTER;");
        batimentCol.setStyle("-fx-alignment: CENTER;");

        // données
        donnees_salles = FXCollections.observableList(liste_des_salles);
        tableviewSalles.setItems(donnees_salles);
    }
}
