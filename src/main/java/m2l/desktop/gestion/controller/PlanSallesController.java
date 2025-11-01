/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2l.desktop.gestion.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import m2l.desktop.gestion.PopupSalleDescription;
import m2l.desktop.gestion.model.ModelQueries;
import m2l.desktop.gestion.model.PlanSalle;
import m2l.desktop.gestion.model.Salle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author nathalie
 */
public class PlanSallesController implements Initializable {

    //membres liés à un élémment graphique dans le FXML
    @FXML
    public Label rezDeChaussee;
    public Label batA;

    @FXML
    public AnchorPane zonePlan;

    //Membres standards
    private Group batimentD, batimentC, batimentB, batimentA, HallAccueil, pins;

    private List<Salle> listeSalles;
    private List<PlanSalle> listePlanSalles;

    private int xleft = 50, ytop = 25, ybottom = 250;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.listePlanSalles = new ArrayList<>();



        System.out.println("Chargement des salles...");

        //récupération des salles depuis l'API
        listeSalles = ModelQueries.getSallesFromApi();

        this.pins = new Group();

        afficherRezDeChaussee();

    }

    public Salle trouverSalleParNom(String nom) {
        for (Salle s : this.listeSalles) {
            System.out.println("Comparaison entre " + nom + " et " + s.getNom());
            if (nom.equals(s.getNom())) {
                return s;
            }
        }
        return null; // ou une autre valeur indiquant que la personne n'a pas été trouvée
    }

    private void initialiserBatimentD() {
        this.batimentD = new Group();

        int x = this.xleft;
        int y1 = this.ytop;

        int h1 = 75;
        int h2 = 35;
        int h3 = 140;
        int h4 = 50;

        int w = 100;

        int y2 = y1 + h1;
        int y3 = y2 + h2;
        int y4 = y3 + h3;

        for(Salle s: this.listeSalles){
            if(s.getBatiment().equals("D")){
                System.out.println("Salle du batiment D : " + s.getNom());

                switch(s.getNom()){
                    case "marjorelle":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x, y1, w, h1)));
                        break;
                    case "cuisine":
                        this.listePlanSalles.add(new PlanSalle(s,new Rectangle(x, y2, w, h2)));
                        break;
                    case "restauration et convivialité":
                        this.listePlanSalles.add(new PlanSalle(s,new Rectangle(x, y3, w, h3)));
                        break;
                    case "reprographie":
                        this.listePlanSalles.add(new PlanSalle(s,new Rectangle(x, y4, w, h4)));
                        break;
                }
            }
        }
    }

    private void initialiserBatimentC() {
        this.batimentC = new Group();

        //hauteur de la salle marjorelle
        int h = 75;//(int) this.listePlanSalles.get(0).r.getHeight();
        int w = 75;

        int x = this.xleft;
        int y1 = this.ybottom + w;
        int c = h / 2;
        int y2 = y1 + h - c;


        int x1 = x + w;
        int x2 = x1 + c;
        int x3 = x2 + c;

        for(Salle s: this.listeSalles) {
            if (s.getBatiment().equals("C")) {
                System.out.println("Salle du batiment C : " + s.getNom());

                switch (s.getNom()) {
                    case "grüber":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x, y1, w, h)));
                        break;
                    case "Administration M2L 1":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x1, y2, c, c)));
                        break;
                    case "Administration M2L 2":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x2, y2, c, c)));
                        break;
                    case "Administration M2L 3":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x3, y2, c, c)));
                        break;
                }
            }
        }

    }



    private void initialiserBatimentB() {
        this.batimentB = new Group();

        //X de la salle marjorelle +200
        int x = this.xleft + 200;//(int) this.listePlanSalles.get(0).r.getX() + 200;//500;
        int y = this.ytop;

        int h1 = 30;
        int h2 = 235;
        int h3 = 30;
        int h4 = h3 + 10;

        int w = 150;
        int w1 = 115;
        int w2 = w - w1;
        int w3 = 75;
        int w4 = w3 + 20;

        int y2 = y+h1;
        int y3 = y2 + h2;

        for(Salle s: this.listeSalles) {
            if (s.getBatiment().equals("B")) {
                System.out.println("Salle du batiment B : " + s.getNom());

                switch (s.getNom()) {
                    case "lamour":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x, y, w, h1)));
                        break;
                    case "amphithéatre": System.out.println("Création de l'amphi");
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x, y2, w, h2)));
                        break;
                    case "Salle de services":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x, y3, w1, h3)));
                        break;

                    case "longwy":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x + w1 + w2 + 20, y3 - 20, w3, h4)));
                        break;
                    case "multimédia":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x + w, y3 - 50 - h4, w4, h4 + 30)));
                        break;
                }


                this.listePlanSalles.add(new PlanSalle(new Salle(999,"Aucune information","B"),  new Rectangle(x + w1, y3, w2, h3)));

            }
        }
    }

    private void initialiserBatimentA() {
        this.batimentA = new Group();

        //X de la salle marjorelle +370
        int x1 = this.xleft + 370;//(int) this.listePlanSalles.get(0).r.getX() + 370;
        //25+30+235 + 40 -20 - 310
        int y1 = this.ybottom + 60;
        int c = 80;
        int x2 = x1 +c ;
        int x3 = x2 + c;
        int x4 = x3 + c;

        for(Salle s: this.listeSalles) {
            if (s.getBatiment().equals("A")) {
                System.out.println("Salle du batiment A : " + s.getNom());

                switch (s.getNom()) {
                    case "daum":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x1, y1, c, c)));
                        break;
                    case "gallé":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x2, y1, c, c)));
                        break;
                    case "corbin":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x3, y1, c, c)));
                        break;
                    case "baccarat":
                        this.listePlanSalles.add(new PlanSalle(s, new Rectangle(x4, y1, c, c)));
                        break;
                }
            }
        }

    }

    private void initialiserHallAccueil() {
        this.HallAccueil = new Group();

        this.listePlanSalles.add(new PlanSalle(new Salle(0,"Ascenceur","H"), new Rectangle(this.xleft + 240, this.ybottom + 125, 75, 25)));

    }

    private void ajouterGestionEvenement(Circle c) {
        final Tooltip tooltip = new Tooltip();


        //configuration du tooltip
        c.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String crid = ((Circle) e.getSource()).getId();
                int id = Integer.valueOf(crid);
                String nomsalle = listePlanSalles.get(id).getSalle().getNom();

                ((Circle) e.getSource()).setFill(Color.DARKSLATEBLUE);

                //récupération de la fenêtre
                Scene scene = (Scene) ((Circle) e.getSource()).getScene();
                Stage stage = (Stage) scene.getWindow();


                if (tooltip.isShowing() == false) {
                    tooltip.setText(nomsalle + "\n");
                    tooltip.setAnchorX(e.getScreenX() + c.getRadius() * 1.8);
                    tooltip.setAnchorY(e.getScreenY() + c.getRadius() * 2);
                    tooltip.show(stage);
                }
            }
        });


        c.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                tooltip.hide();
                ((Circle) e.getSource()).setFill(Paint.valueOf("CF2140"));
            }
        });

        c.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                String crid = ((Circle) e.getSource()).getId();
                int id = Integer.valueOf(crid);
                String nomsalle = listePlanSalles.get(id).getSalle().getNom();

                //récupération de la fenêtre
                Scene scene = (Scene) ((Circle) e.getSource()).getScene();
                Stage stage = (Stage) scene.getWindow();

                new PopupSalleDescription(stage, nomsalle);
            }
        });
    }

    public void afficherRezDeChaussee() {

        if(this.batimentD!=null) this.batimentD.getChildren().clear();
        if(this.batimentC!=null) this.batimentC.getChildren().clear();
        if(this.batimentB!=null) this.batimentB.getChildren().clear();
        if(this.batimentA!=null) this.batimentA.getChildren().clear();
        if(this.HallAccueil!=null) this.HallAccueil.getChildren().clear();
        if(this.pins!=null) this.pins.getChildren().clear();
        if(this.zonePlan!=null) this.zonePlan.getChildren().clear();
        this.listePlanSalles.clear();

        initialiserBatimentD();
        initialiserBatimentC();
        initialiserBatimentB();
        initialiserBatimentA();
        initialiserHallAccueil();

        configurerPinsSalles();

        if (!this.zonePlan.getChildren().contains(batimentD)) this.zonePlan.getChildren().add(batimentD);
        if (!this.zonePlan.getChildren().contains(batimentC)) this.zonePlan.getChildren().add(batimentC);
        if (!this.zonePlan.getChildren().contains(batimentB)) this.zonePlan.getChildren().add(batimentB);
        if (!this.zonePlan.getChildren().contains(batimentA)) this.zonePlan.getChildren().add(batimentA);
        if (!this.zonePlan.getChildren().contains(HallAccueil)) this.zonePlan.getChildren().add(HallAccueil);
        if (!this.zonePlan.getChildren().contains(pins)) this.zonePlan.getChildren().add(pins);
    }

    @FXML
    public void affichePlanBatA() {
        if(this.batimentD!=null) this.batimentD.getChildren().clear();
        if(this.batimentC!=null) this.batimentC.getChildren().clear();
        if(this.batimentB!=null) this.batimentB.getChildren().clear();
        if(this.batimentA!=null) this.batimentA.getChildren().clear();
        if(this.HallAccueil!=null) this.HallAccueil.getChildren().clear();
        if(this.pins!=null) this.pins.getChildren().clear();
        if(this.zonePlan!=null) this.zonePlan.getChildren().clear();
        this.listePlanSalles.clear();
    }

    private void configurerPinsSalles() {
        for (int i = 0; i < this.listePlanSalles.size(); i++) {

            Circle c = new Circle((int) (this.listePlanSalles.get(i).r.getX() + this.listePlanSalles.get(i).r.getWidth() / 2), (int) (this.listePlanSalles.get(i).r.getY() + this.listePlanSalles.get(i).r.getHeight() / 2), 10);
            c.setFill(Paint.valueOf("CF2140"));
            c.setId("" + i);

            ajouterGestionEvenement(c);

            this.pins.getChildren().add(c);

            this.listePlanSalles.get(i).r.setFill(Paint.valueOf("D8EAF5"));
            this.listePlanSalles.get(i).r.setStroke(Paint.valueOf("5672F5"));
            //this.listeSalles.get(i)

            switch (this.listePlanSalles.get(i).getSalle().getBatiment()) {
                case "D":
                    this.batimentD.getChildren().add(this.listePlanSalles.get(i).r);
                    break;
                case "C":
                    this.batimentC.getChildren().add(this.listePlanSalles.get(i).r);
                    break;
                case "B":
                    this.batimentB.getChildren().add(this.listePlanSalles.get(i).r);
                    break;
                case "A":
                    this.batimentA.getChildren().add(this.listePlanSalles.get(i).r);
                    break;
                case "H":
                    this.HallAccueil.getChildren().add(this.listePlanSalles.get(i).r);
                    break;

            }
        }
    }

}
