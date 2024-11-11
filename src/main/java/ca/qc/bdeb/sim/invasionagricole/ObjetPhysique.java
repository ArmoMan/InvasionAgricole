package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class ObjetPhysique {

    protected double posX, posY;
    protected double vX, vY;
    protected double aX = 0, aY = 0;
    private Image imagePersonnage;
    protected double largeur, hauteur;

    public ObjetPhysique(int posX, int posY, String pathImagePersonnage) {
        this.posX = posX;
        this.posY = posY;
        this.imagePersonnage = new Image(pathImagePersonnage);
    }


    protected void updatePhysique(double deltaTemps) {

        vX += deltaTemps * aX;
        vY += deltaTemps * aY;
        posX += deltaTemps * vX;
        posY += deltaTemps * vY;
    }

    protected abstract void dessiner(GraphicsContext context, Camera camera);


    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
