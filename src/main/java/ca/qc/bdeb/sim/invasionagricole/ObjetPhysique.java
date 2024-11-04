package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class ObjetPhysique {

    private int posX, posY;
    private int vX, vY;
    private int aX, aY;
    private Image imagePersonnage;
    private double largeur, hauteur;

    public ObjetPhysique(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    protected abstract void  updatePhysique(double deltaTemps);

    protected abstract void desssiner(GraphicsContext context);


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
