package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class ObjetPhysique {

    private int posX, posY;
    private int vX, vY;
    private int aX, aY;
    private Image imagePersonnage;
    protected double largeur, hauteur;

    public ObjetPhysique(int posX, int posY, String pathImagePersonnage) {
        this.posX = posX;
        this.posY = posY;
        this.imagePersonnage = new Image(pathImagePersonnage);
    }

    protected abstract void  updatePhysique(double deltaTemps);

    protected abstract void dessiner(GraphicsContext context, Camera camera);


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
