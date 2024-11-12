package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class ObjetPhysique {

    protected double posX, posY;
    protected double vX, vY;
    protected double aX = 0, aY = 0;
    protected Image imagePersonnage;
    protected double largeur, hauteur;
    protected int nombreDeVie;

    public ObjetPhysique(int posX, int posY, String pathImagePersonnage) {
        this.posX = posX;
        this.posY = posY;
        this.imagePersonnage = new Image(pathImagePersonnage);
    }

    public abstract void update(double deltaTime);

    protected void updatePhysique(double deltaTemps) {

        vX += deltaTemps * aX;
        vY += deltaTemps * aY;
        posX += deltaTemps * vX;
        posY += deltaTemps * vY;
    }

    public abstract void dessiner(GraphicsContext context, Camera camera);

    /**
     * Methode pour dessiner les lignes jaunes des ObjetPhysiques selon largeur et longeur
     * @param context
     * @param camera
     */
    protected void dessinerDebug(GraphicsContext context, Camera camera) {
        double xEcran = camera.calculerEcranX(posX);
        double yEcran = camera.calculerEcranY(posY);

        context.setStroke(Color.YELLOW);
        context.setLineWidth(2);
        context.strokeRect(xEcran, yEcran, largeur, hauteur);
    }


    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public int getNombreDeVie() {
        return nombreDeVie;
    }

    public void setNombreDeVie(int nombreDeVie) {
        this.nombreDeVie = nombreDeVie;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLargeur() {
        return largeur;
    }
}
