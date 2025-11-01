package m2l.desktop.gestion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import m2l.desktop.gestion.GestionLocaux;
import m2l.desktop.gestion.model.Climatiseur;
import m2l.desktop.gestion.model.Intervention;
import m2l.desktop.gestion.model.ModelQueries;
import m2l.desktop.gestion.model.Tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClimsTableviewController implements Initializable {


    @FXML
    private TableView<Climatiseur> tableviewClimatiseurs;

    @FXML
    private TableColumn<Climatiseur, String> marqueCol;
    @FXML
    private TableColumn<Climatiseur, String> modeleCol;
    @FXML
    private TableColumn<Climatiseur, Number> puissanceCol;
    @FXML
    private TableColumn<Climatiseur, Number> surface_minCol;
    @FXML
    private TableColumn<Climatiseur, Number> surface_maxCol;
    @FXML
    private TableColumn<Climatiseur, String> batimentCol;
    @FXML
    private TableColumn<Climatiseur, String> salleCol;

    private List<Climatiseur> liste_des_climatiseurs;

    private static ObservableList<Climatiseur> donnees_climatiseurs;

    public static ObservableList<Climatiseur> getListeClimatiseurs() {
        return donnees_climatiseurs;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            System.out.println("Chargement des climatiseurs dans le tableView...");


            liste_des_climatiseurs = ModelQueries.getClimatiseursFromApi();

            // Afficher les objets Personne
            for (Climatiseur c : liste_des_climatiseurs) {
                System.out.println(c);
            }

            //le  tableview sera éditable
            tableviewClimatiseurs.setEditable(true);

            //la marque pourra être modifiée
            //dans le tableview en double-cliquant dessus
            modeleCol.setCellFactory(TextFieldTableCell.forTableColumn());

            //mise en correspondance de la colonne "modeleCol" du tableview
            //avec la propriété "modele" de la classe Climatiseur
            modeleCol.setCellValueFactory(cell -> cell.getValue().modeleProperty());

            //mise en correspondance de la colonne "marqueCol" du tableview
            //avec la propriété "marque" de la classe Climatiseur
            marqueCol.setCellValueFactory(cell -> cell.getValue().marqueProperty());

            //mise en correspondance de la colonne "salleCol" du tableview
            //avec la propriété "nom" de la salle de la classe Intervention
            salleCol.setCellValueFactory(cell -> cell.getValue().getSalle().nomProperty());

            //mise en correspondance de la colonne "intervenantCol" du tableview
            //avec la concaténation "prénom nom" de l'intervenant de la classe Intervention
            batimentCol.setCellValueFactory(cell -> cell.getValue().getSalle().batimentProperty());

            //création de la liste qui correspondra au contenu
            //du tableview
            donnees_climatiseurs = FXCollections.observableList(liste_des_climatiseurs);
            //mise en correspondance de la liste "donneesIntJour"
            //avec le tableview "todayInt"
            tableviewClimatiseurs.setItems(donnees_climatiseurs);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void popupAjoutClimatiseur(MouseEvent mouseEvent) throws IOException {


        Stage stage_ajout = new Stage();
        try {
            FXMLLoader root = new FXMLLoader(GestionLocaux.class.getResource("formulaire_nouveau_climatiseur.fxml"));
            Scene scene = new Scene(root.load());

            stage_ajout.setScene(scene);
            stage_ajout.initOwner(tableviewClimatiseurs.getScene().getWindow());
            stage_ajout.setTitle("Ajout d'un nouveau climatiseur");
            stage_ajout.initModality(Modality.WINDOW_MODAL);
            stage_ajout.show();
        } catch (IOException e) {
            System.err.println(getClass().getName() + " : Il y a une erreur lors de laffichage de la fenêtre d'ajout.");
            e.printStackTrace();
        }
    }

    public void suppressionClimatiseur(MouseEvent mouseEvent) {
        //récupération du climatiseur sélectionné
        Climatiseur selectedItem = (Climatiseur) tableviewClimatiseurs.getSelectionModel().getSelectedItem();

        System.out.println("Suppression du climatiseur : " + selectedItem);

        ModelQueries.deleteClimatiseur(selectedItem);
        ClimsTableviewController.getListeClimatiseurs().remove(selectedItem);

    }

    public void modificationClimatiseur(TableColumn.CellEditEvent cellEditEvent) throws IOException {
        System.out.println("La valeur est modifiée pour : "
                + cellEditEvent.getTableColumn().getText()
                + " = " + cellEditEvent.getNewValue().toString());

        Climatiseur c = tableviewClimatiseurs.getSelectionModel().getSelectedItem();
        c.setModele(cellEditEvent.getNewValue().toString());
        //Mettre à jour dans la BDD
         ModelQueries.updateClimatiseur(c);

    }

    public void voirFournisseur(MouseEvent mouseEvent) {

        Climatiseur selectedItem = (Climatiseur) tableviewClimatiseurs.getSelectionModel().getSelectedItem();
        System.out.println(getClass().getName() + " : Climatiseur sélectionné dans la liste des climatiqeurs " + selectedItem);

        popupFournisseur(selectedItem);
    }

    private void popupFournisseur(Climatiseur c) {


        Stage stage_ajout = new Stage();

        stage_ajout.setOnCloseRequest(event -> {
            // Action à effectuer lorsque la fenêtre est fermée
            System.out.println("La fenêtre a été fermée.");
            // Vous pouvez également ajouter d'autres logiques ici, comme
            // mettre à jour des données dans la fenêtre principale.
        });


        try {
            FXMLLoader root = new FXMLLoader(GestionLocaux.class.getResource("detailsFournisseur.fxml"));
            Scene scene = new Scene(root.load());

            stage_ajout.setScene(scene);
            stage_ajout.initOwner(tableviewClimatiseurs.getScene().getWindow());
            stage_ajout.setTitle("Fournisseur de la marque");
            stage_ajout.initModality(Modality.WINDOW_MODAL);
            stage_ajout.show();
        } catch (IOException e) {
            System.err.println(getClass().getName() + " : Impossible de lancer la fenêtre popup.");
            e.printStackTrace();
        }
    }

}


