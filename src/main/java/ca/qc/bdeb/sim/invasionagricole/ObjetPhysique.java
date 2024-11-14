package ca.qc.bdeb.sim.invasionagricole;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class ObjetPhysique {

    protected double posX=0, posY=0;
    protected double vX, vY;
    protected double aX = 0, aY = 0; // jai changer de 0 a 1 pour l'acceleration, je sais pas si ca brise quelque chose
    protected Image imagePersonnage;
    protected double largeur, hauteur;
    protected int nombreDeVie;
    protected double largeurNiveau, hauteurNiveau;
    protected double garvite = 1000;
    // Boolean pour voir si l'objet est en collsion entre les limites horizontales du niveau
    protected boolean estEnCollisionHorizontal = false;


    public ObjetPhysique(double posX, double posY, String pathImagePersonnage,double largeurNiveau, double hauteurNiveau) {
        this.posY = posY;
        this.posX = posX;
        this.imagePersonnage = new Image(pathImagePersonnage);
        this.largeurNiveau = largeurNiveau;
        this.hauteurNiveau = hauteurNiveau;
        this.aY = garvite;
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
    protected void verifierQuePasHorsNiveau(double largeurMax, double hauteurMax, double pourcentageHauteurANePasDepasser){

        estEnCollisionHorizontal = true;
        // verifier la limite horizontale
        if (posX + largeur > largeurMax) {
            posX = largeurMax - largeur;
            vX = 0;
        }else if (posX < 0) {
            posX = 0;
            vX = 0;
        }else {
            // si pas hors limites
            estEnCollisionHorizontal = false;
        }

        // verifier que le vaisseau ne depasse pas pourcentageHauteurANePasDepasser de l'hauteur du niveau
        double limiteDeHauteur = hauteurMax * (pourcentageHauteurANePasDepasser/100);
        if (posY + hauteur > limiteDeHauteur) {
            posY = limiteDeHauteur - hauteur;
            vY = 0;
        }else if (posY < 0) {
            posY = 0;
            vY = 0;
        }

    }

    /**
     * Methode pour generer une position x entre 0 et largeurNiveau - largeur (image)
     * @return
     */
    protected double genererPositionX(){
        return (Math.random() * (largeurNiveau-largeur));
    }

    public boolean estEnCollisionAvec(ObjetPhysique objetPhysique) {

        // Verifier si objet est entre les x du rayon
        boolean estCeQuePlusGaucheQuePartieDroiteDeCa = this.getGauche()
                + this.largeur > objetPhysique.getGauche();
        boolean estCeQuePlusDroiteQuePartieGaucheDeCa = this.getGauche() < objetPhysique.getDroite();

        // Verifier si objet est entre les y du rayon
        // Verifier si partie le plus bas de l'objet est plus bas que le point le plus haut de this object
        boolean estCeQuePlusBasQuePartieHautDeCa = this.getPosY() < objetPhysique.getPosY()
                + objetPhysique.getHauteur();
        // Verifier si partie le plus haut de l'objet est plus haut que le point le plus bas de this object
        boolean estCeQuePlusHautQuePartieBasDeCa = objetPhysique.getPosY() < this.getPosY() + hauteur;


        return estCeQuePlusBasQuePartieHautDeCa && estCeQuePlusHautQuePartieBasDeCa
                && estCeQuePlusDroiteQuePartieGaucheDeCa && estCeQuePlusGaucheQuePartieDroiteDeCa;

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

    public double getGarvite() {
        return garvite;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public void setaX(double aX) {
        this.aX = aX;
    }

    public void setaY(double aY) {
        this.aY = aY;
    }
}
