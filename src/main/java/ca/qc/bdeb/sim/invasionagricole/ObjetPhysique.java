package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class ObjetPhysique {

    protected double posX, posY;
    protected double vX, vY;
    protected double aX = 0, aY = 0; // jai changer de 0 a 1 pour l'acceleration, je sais pas si ca brise quelque chose
    protected Image imagePersonnage;
    protected double largeur, hauteur;
    protected int nombreDeVie;


    public ObjetPhysique(double posX, double posY, String pathImagePersonnage) {
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

    /**
     * Methode pour verifier que le vaisseau n'est pas hors ecran et qu'il respecte la limite de hauteur
     * @param largeurMax du niveau
     * @param hauteurMax du niveau
     */
    protected void verifierQuePasHorsNiveau(double largeurMax, double hauteurMax){

        // verifier la limite horizontale
        if (posX + largeur > largeurMax) {
            posX = largeurMax - largeur;
            vX = 0;
        }else if (posX < 0) {
            posX = 0;
            vX = 0;
        }

        // verifier que le vaisseau ne depasse pas 60% de l'hauteur du niveau
        double limiteDeHauteur = hauteurMax * 0.6;
        if (posY + hauteur > limiteDeHauteur) {
            posY = limiteDeHauteur - hauteur;
            vY = 0;
        }else if (posY < 0) {
            posY = 0;
            vY = 0;
        }

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

    public double getGauche(){
        return this.getPosX();
    }

    public double getDroite(){
        return this.getPosX() + this.largeur;
    }
}
