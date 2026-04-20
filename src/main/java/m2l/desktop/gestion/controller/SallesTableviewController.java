package m2l.desktop.gestion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import m2l.desktop.gestion.model.ModelQueries;
import m2l.desktop.gestion.model.Salle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
