module m2l.desktop.gestion.gestion_m2l {
    requires javafx.controls;
    requires javafx.fxml;


    //Permet d'ouvrir le package gestion à la librairie JavaFX pour le chargement des fichiers FXML
    opens m2l.desktop.gestion to javafx.fxml;
    //Permet d'ouvrir le package controller à la librairie JavaFX pour le chargement des fichiers FXML
    opens m2l.desktop.gestion.controller to javafx.fxml;
    //Permet de rendre les classes du package modele accessible à la librairie Gson pour la sérialisation/désérialisation
    opens m2l.desktop.gestion.model to javafx.base,com.google.gson;

    exports m2l.desktop.gestion;
    requires com.google.gson;
}