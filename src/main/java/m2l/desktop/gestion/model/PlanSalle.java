package m2l.desktop.gestion.model;

import javafx.scene.shape.Rectangle;

public class PlanSalle {

    private Salle salle;
    public Rectangle r;

    public PlanSalle(Salle salle, Rectangle r)
    {
        this.salle = salle;
        this.r = r;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
